接口框架更改记录
===============

框架使用事项
-------------

* 约定
 - 代码参考 com.hezy.modules.core 下的代码
 - 包结构:  com.hezy.modules.模块 ， 模块下可分为：rest、service、entity、form、dao
 - 尽量采用 commons-lang3 和 guava 工具类，json尽量使用jackson和json-java，逐步废弃gson
 - 单元测试可以使用resteasy-client，可以加到版本库中，包结构:com.hezy.modules.模块.rest , 在maven时 mvn clean package -D maven.test.skip=true 避免执行测试
 - 在Eclipse工程， api/src/main/webapp/WEB-INF/lib 下为本地jar，不要放到git上。jar包可通过maven获得。

* 目前代码必须完成的改造
 - DAO 层需要加入 @Repository 标注
 - Service层（接口层）改造：1、加入 @Service @Transactional 标注， 2、 dao类采用 @Autowired 注入
 - RestService层改造： 1、加入 @Component标注 ， 2、服务、接口类采用 @Autowired 注入



更改记录
-------------

* 框架更改：
 - 引入spring注入框架
 - 升级resteasy、hibernate、netty等等依赖jar
 - 日志引入slf4j

* 新增的依赖包
 - springframework 4.2.4.RELEASE
 - aspectj 1.6.10
 - shiro 1.3.2
 - slf4j 1.7.21


* 去掉的依赖包
 - ehcache-web-2.0.4
 - hibernate-search
 - hibernate-entitymanager
 - alipay-sdk-java
 - com.google.zxing
 - QrCode
 - c3p0-0.9.2-pre4 (自己打包)采用 c3p0-0.9.2.1


* 依赖包版本升级
 - resteasy 3.0.19.Final
 - netty 4.1.6.Final
 - hibernate 4.2.21.Final
 - hibernate-validator 5.3.3.Final
 - jackson 2.8.3
 - guava 19.0
 - mysql-connector 5.1.40

* 源代码更改：
 - ConfigUtils 去掉方法 getTwoCode （二维码相关、没有用到）
 - DataEntity 去掉 hibernate search相关的标注
 - 临时删除2个filter，需要按照3.0的resteasy规范重新改造一下。
 - HibernateUtil去掉静态加载配置文件的方法initHibernate()，采用spring注入配置。
 - 删除类 MessageApplication
 - hibernate baseDao 修改大量代码
 - hibernateUtils 删除除了validate外的代码
 - commons-lang使用了2.6和3.5两个版本，统一采用3.5
 - hibernate基类除了sessionManager改为注入方式，重新修改了baseDao。
 - filter改造
 - configUtils工具类改造
 - msgUtils工具类废弃，使用restMsgUtils

* 目前存在遗留问题
 - json使用了3个框架：jackson、gson、json-java，后续逐渐统一。
 - 日志输出整理文件配置
 