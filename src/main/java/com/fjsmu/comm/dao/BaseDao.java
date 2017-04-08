package com.fjsmu.comm.dao;

import com.fjsmu.comm.entity.BaseEntity;
import com.fjsmu.comm.entity.Page;
import com.fjsmu.tools.ConvertUtils;
import com.fjsmu.tools.Reflections;
import com.fjsmu.tools.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DAO支持类实现
 *
 * @param <T>
 * @author ThinkGem
 * @version 2013-05-15
 */
@Repository
public class BaseDao<T> {

    private static Logger logger = Logger.getLogger(BaseDao.class);
    /**
     * SessionFactory
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 实体类类型(由构造方法自动赋值)
     */
    private Class<?> entityClass;

    /**
     * 构造方法，根据实例类自动获取实体类类型
     */
    public BaseDao() {
        entityClass = Reflections.getClassGenricType(getClass());
    }

    /**
     * 获取 Session
     */
    public Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        return session;
    }

    /**
     * 强制与数据库同步
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * 清除单个对象缓存
     */
    public void Evict(Class<?> entityClass) {
        getSession().evict(entityClass);
    }

    /**
     * 清除缓存数据
     */
    public void clear() {
        getSession().clear();
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public T get(Serializable id) throws Exception {
        return (T) getSession().get(entityClass, id);
    }

    /**
     * QL 查询
     *
     * @param qlString
     * @return
     * @throws Exception
     */
    public <E> List<E> find(String qlString) throws Exception {
        return find(qlString, null);
    }

    /**
     * QL 查询
     *
     * @param qlString
     * @param parameter
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> find(String qlString, Parameter parameter) throws Exception {
        Query query = createQuery(qlString, parameter);
        return query.list();
    }

    /**
     * QL 查询所有
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() throws Exception {
        Session session = getSession();
        Criteria c = session.createCriteria(entityClass);
        // c.setCacheable(true);
        return c.list();
    }

    /**
     * 创建 QL 查询对象
     *
     * @param qlString
     * @param parameter
     * @return
     */
    public Query createQuery(String qlString, Parameter parameter) {
        Query query = getSession().createQuery(qlString);
        // query.setCacheable(true);
        setParameter(query, parameter);
        return query;
    }

    /**
     * 设置查询参数
     *
     * @param query
     * @param parameter
     */
    public void setParameter(Query query, Parameter parameter) {
        if (parameter != null) {
            Set<String> keySet = parameter.keySet();
            for (String string : keySet) {
                Object value = parameter.get(string);
                // 这里考虑传入的参数是什么类型，不同类型使用的方法不同
                if (value instanceof Collection<?>) {
                    query.setParameterList(string, (Collection<?>) value);
                } else if (value instanceof Object[]) {
                    query.setParameterList(string, (Object[]) value);
                } else {
                    query.setParameter(string, value);
                }
            }
        }

    }

    /**
     * 获取实体
     *
     * @param qlString
     * @return
     * @throws Exception
     */
    public T getByHql(String qlString) throws Exception {
        return getByHql(qlString, null);
    }

    /**
     * 获取实体
     *
     * @param qlString
     * @param parameter
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public T getByHql(String qlString, Parameter parameter) throws Exception {
        Query query = createQuery(qlString, parameter);
        query.setCacheable(true);
        return (T) query.uniqueResult();
    }

    /**
     * 保存实体
     *
     * @param entity 是否自动提交
     * @throws Exception
     */
    public void save(T entity) throws Exception {
        try {
            // 获取实体编号
            Object id = null;
            for (Method method : entity.getClass().getMethods()) {
                Id idAnn = method.getAnnotation(Id.class);
                if (idAnn != null) {
                    id = method.invoke(entity);
                    break;
                }
            }
            // 插入前执行方法
            if (StringUtils.isBlank((String) id)) {
                for (Method method : entity.getClass().getMethods()) {
                    PrePersist pp = method.getAnnotation(PrePersist.class);
                    if (pp != null) {
                        method.invoke(entity);
                        break;
                    }
                }
            }
            // 更新前执行方法
            else {
                for (Method method : entity.getClass().getMethods()) {
                    PreUpdate pu = method.getAnnotation(PreUpdate.class);
                    if (pu != null) {
                        method.invoke(entity);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        getSession().saveOrUpdate(entity);

    }

    /**
     * 保存实体列表 （自动提交）
     *
     * @param entityList
     * @throws Exception
     */
    public void save(List<T> entityList) throws Exception {
        for (T entity : entityList) {
            save(entity);
        }
    }

    /**
     * 更新（自动提交）
     *
     * @param qlString
     * @return
     * @throws Exception
     */
    public int update(String qlString) throws Exception {
        return update(qlString, null);
    }

    /**
     * 更新
     *
     * @param qlString
     * @param parameter 是否自动提交
     * @return
     * @throws Exception
     */
    public int update(String qlString, Parameter parameter) throws Exception {
        return createQuery(qlString, parameter).executeUpdate();
    }

    /**
     * 逻辑删除（自动提交）
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteById(Serializable id) throws Exception {
        return update("update " + entityClass.getSimpleName() + " set delFlag='" + BaseEntity.DEL_FLAG_DELETE + "' where id = :p1", new Parameter(id));
    }

    /**
     * 更新删除标记（自动提交）
     *
     * @param id
     * @param delFlag
     * @return
     * @throws Exception
     */
    public int updateDelFlag(Serializable id, String delFlag) throws Exception {
        return update("update " + entityClass.getSimpleName() + " set delFlag = :p2 where id = :p1", new Parameter(id, delFlag));
    }

    /**
     * SQL 查询
     *
     * @param sqlString
     * @return
     * @throws Exception
     */
    public <E> List<E> findBySql(String sqlString) throws Exception {
        return findBySql(sqlString, null, null);
    }

    /**
     * SQL 查询
     *
     * @param sqlString
     * @param parameter
     * @return
     * @throws Exception
     */
    public <E> List<E> findBySql(String sqlString, Parameter parameter) throws Exception {
        return findBySql(sqlString, parameter, null);
    }

    /**
     * SQL 查询
     *
     * @param sqlString
     * @param resultClass
     * @param parameter
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> findBySql(String sqlString, Parameter parameter, Class<?> resultClass) throws Exception {
        SQLQuery query = createSqlQuery(sqlString, parameter);
        setResultTransformer(query, resultClass);
        return query.list();
    }

    /**
     * SQL 更新(自动提交)
     *
     * @param sqlString
     * @param parameter
     * @return
     * @throws Exception
     */
    public int updateBySql(String sqlString, Parameter parameter) throws Exception {
        return updateBySql(sqlString, parameter, true);
    }

    /**
     * SQL 更新
     *
     * @param sqlString
     * @param parameter
     * @return
     * @throws Exception
     */
    public int updateBySql(String sqlString, Parameter parameter, boolean isCommitAuto) throws Exception {
        return createSqlQuery(sqlString, parameter).executeUpdate();
    }

    /**
     * 创建 SQL 查询对象
     *
     * @param sqlString
     * @param parameter
     * @return
     * @throws Exception
     */
    private SQLQuery createSqlQuery(String sqlString, Parameter parameter) throws Exception {
        SQLQuery query = getSession().createSQLQuery(sqlString);
        // query.setCacheable(true);
        setParameter(query, parameter);
        return query;
    }

    /**
     * limit 查询输出
     *
     * @param qlString
     * @param parameter
     * @param limit
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> findLimit(String qlString, Parameter parameter, int limit) throws Exception {
        Query query = createQuery(qlString, parameter);
        query.setFirstResult(0);
        query.setMaxResults(limit);
        return query.list();
    }

    // -------------- Query Tools --------------

    /**
     * 设置查询结果类型
     *
     * @param query
     * @param resultClass
     * @throws Exception
     */
    private void setResultTransformer(SQLQuery query, Class<?> resultClass) throws Exception {
        if (resultClass != null) {
            if (resultClass == Map.class) {
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            } else if (resultClass == List.class) {
                query.setResultTransformer(Transformers.TO_LIST);
            } else {
                query.addEntity(resultClass);
            }
        }
    }

    /**
     * 使用检索标准对象查询
     *
     * @param detachedCriteria
     * @return
     * @throws Exception
     */
    public List<T> find(DetachedCriteria detachedCriteria) throws Exception {
        return find(detachedCriteria, Criteria.DISTINCT_ROOT_ENTITY);
    }

    /**
     * 使用检索标准对象查询
     *
     * @param detachedCriteria
     * @param resultTransformer
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<T> find(DetachedCriteria detachedCriteria, ResultTransformer resultTransformer) throws Exception {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        // criteria.setCacheable(true);
        criteria.setResultTransformer(resultTransformer);
        return criteria.list();
    }

    /**
     * 使用检索标准对象查询记录数
     *
     * @param detachedCriteria
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public long count(DetachedCriteria detachedCriteria) throws Exception {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        // criteria.setCacheable(true);
        long totalCount = 0;
        try {
            // Get orders
            Field field = CriteriaImpl.class.getDeclaredField("orderEntries");
            field.setAccessible(true);
            List orderEntrys = (List) field.get(criteria);
            // Remove orders
            field.set(criteria, new ArrayList());
            // Get count
            criteria.setProjection(Projections.rowCount());
            totalCount = Long.valueOf(criteria.uniqueResult().toString());
            // Clean count
            criteria.setProjection(null);
            // Restore orders
            field.set(criteria, orderEntrys);
        } catch (NoSuchFieldException e) {
            logger.warn(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.warn(e.getMessage(), e);
        }
        return totalCount;
    }

    /**
     * 创建与会话无关的检索标准对象
     *
     * @param criterions Restrictions.eq("name", value);
     * @return
     * @throws Exception
     */
    public DetachedCriteria createDetachedCriteria(Criterion... criterions) {
        DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
        for (Criterion c : criterions) {
            dc.add(c);
        }
        return dc;

    }

    // -------------- QL Query --------------

    /**
     * QL 分页查询
     *
     * @param page
     * @param qlString
     * @return
     */
    public <E> Page<E> find(Page<E> page, String qlString) throws Exception {
        return find(page, qlString, null);
    }

    /**
     * QL 分页查询
     *
     * @param page
     * @param qlString
     * @param parameter
     * @return
     */
    @SuppressWarnings("unchecked")
    public <E> Page<E> find(Page<E> page, String qlString, Parameter parameter) throws Exception {
        // get count
        String countQlString = "select count(*) " + removeSelect(removeOrders(qlString));
        Query queryCount = createQuery(countQlString, parameter);
        queryCount.setCacheable(true);
        List<Object> list = queryCount.list();
        if (list.size() > 0) {
            page.setCount(Long.valueOf(list.get(0).toString()));
        } else {
            page.setCount(list.size());
        }
        if (page.getCount() < 1) {
            return page;
        }
        // 算出总页数
        if (page.getCount() % page.getPageSize() == 0) {
            page.setTotalPage(page.getCount() / page.getPageSize());
        } else {
            page.setTotalPage((page.getCount() / page.getPageSize()) + 1);
        }

        // order by
        String ql = qlString;
        if (StringUtils.isNotBlank(page.getOrderBy())) {
            ql += " order by " + page.getOrderBy();
        }
        Query query = createQuery(ql, parameter);
        query.setCacheable(true);

        query.setFirstResult(page.getFirstResult());
        query.setMaxResults(page.getMaxResults());

        page.setList(query.list());
        return page;
    }

    @SuppressWarnings("unchecked")
    public <E> Page<E> find(Page<E> page, String qlString, Parameter parameter, long count) throws Exception {

        page.setCount(count);
        if (page.getCount() < 1) {
            return page;
        }
        // 算出总页数
        if (page.getCount() % page.getPageSize() == 0) {
            page.setTotalPage(page.getCount() / page.getPageSize());
        } else {
            page.setTotalPage((page.getCount() / page.getPageSize()) + 1);
        }

        // order by
        String ql = qlString;
        if (StringUtils.isNotBlank(page.getOrderBy())) {
            ql += " order by " + page.getOrderBy();
        }
        Query query = createQuery(ql, parameter);
        query.setCacheable(true);

        query.setFirstResult(page.getFirstResult());
        query.setMaxResults(page.getMaxResults());

        page.setList(query.list());
        return page;
    }

    @SuppressWarnings("unchecked")
    public Page<Object[]> findBySql(Page<Object[]> page, String sqlString, String sqlCountString, Parameter parameter) throws Exception {
        List<Object[]> listCnt = this.findBySql(sqlCountString, parameter);

        long count = 0;
        if (listCnt != null && listCnt.size() > 0) {
            count = ConvertUtils.parseInt(listCnt.get(0), 0);
        }

        page.setCount(count);
        if (page.getCount() < 1) {
            return page;
        }
        // 算出总页数
        if (page.getCount() % page.getPageSize() == 0) {
            page.setTotalPage(page.getCount() / page.getPageSize());
        } else {
            page.setTotalPage((page.getCount() / page.getPageSize()) + 1);
        }

        SQLQuery query = createSqlQuery(sqlString, parameter);
        query.setFirstResult(page.getFirstResult());
        query.setMaxResults(page.getMaxResults());

        page.setList(query.list());
        return page;
    }

    /**
     * 去除qlString的select子句。
     *
     * @param qlString
     * @return
     */
    private String removeSelect(String qlString) {
        int beginPos = qlString.toLowerCase().indexOf("from");
        return qlString.substring(beginPos);
    }

    /**
     * 去除hql的orderBy子句。
     *
     * @param qlString
     * @return
     */
    private String removeOrders(String qlString) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(qlString);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public int countBySql(String sqlString, Parameter parameter) throws Exception {
        List<Object[]> listCnt = this.findBySql(sqlString, parameter);

        int count = 0;
        if (listCnt != null && listCnt.size() > 0) {
            Object param = listCnt.get(0);
            if (param instanceof Double) {
                count = ((Double) param).intValue(); // TODO Double转int，值大的时候会有问题
            }else{
                count = ConvertUtils.parseInt(param, 0);
            }
        }
        return count;
    }

    public boolean updateOneRow(String sql, Parameter param) {
        try {
            int result = updateBySql(sql, param);
            return (result == 1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}