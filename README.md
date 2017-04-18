接口定义
===============

### 登录接口
```
curl -i -H 'content-type: application/json' -X POST  -d '{"username":"15901177524", "password": "e10adc3949ba59abbe56e057f20f883e"}' http://192.168.1.112:8080/user/login

response:

{
    "errcode": 0,
    "data": {
        "id": "ed4a865e6c224645b7717246d8d7ccfc",
        "remarks": "",
        "createDate": "2017-04-08 14:08:18",
        "updateDate": "2017-04-08 14:08:18",
        "delFlag": "0",
        "loginName": "15901177524",
        "password": "e10adc3949ba59abbe56e057f20f883e",
        "name": "name",
        "head": "",
        "sex": 1,
        "birthday": "2017-01-01",
        "mobile": "15901177524",
        "registerIp": "2017-04-08 14:08:17.0",
        "registerTime": "2017-04-08 14:08:17",
        "loginIp": "192.168.1.112",
        "loginDate": "2017-04-16 12:20:35",
        "token": "51fd9c9eb89442d68c89ca0e60e81fc1",
        "type": 1
    },
    "errmsg": "处理成功"
}
```

### 添加用户接口
```
curl -i -H 'content-type: application/json' -X POST  -d '{"loginName":"18610245972","password":"e10adc3949ba59abbe56e057f20f883e","name":"患者1","head":"https://imgsa.baidu.com/baike/w%3D268/sign=ee8fc6544bfbfbeddc59317940f0f78e/8601a18b87d6277f2a0f655328381f30e924fcb8.jpg","sex":1,"birthday":"2017-01-01","mobile":"18610245972", "type":2}' http://192.168.1.112:8080/user

response:

{
    "errcode": 0,
    "data": {
        "id": "aeb22b0657b94709a7746d3c599e5c98",
        "createDate": "2017-04-16 12:54:53",
        "updateDate": "2017-04-16 12:54:53",
        "delFlag": "0",
        "loginName": "18610245972",
        "password": "e10adc3949ba59abbe56e057f20f883e",
        "name": "患者1",
        "head": "https://imgsa.baidu.com/baike/w%3D268/sign=ee8fc6544bfbfbeddc59317940f0f78e/8601a18b87d6277f2a0f655328381f30e924fcb8.jpg",
        "sex": 1,
        "birthday": "2017-01-01",
        "mobile": "18610245972",
        "type": 2
    },
    "errmsg": "处理成功"
}
```

### 患者挂号

```
curl -i -H 'content-type: application/json' -X POST http://192.168.1.112:8080/encounter/patient/aeb22b0657b94709a7746d3c599e5c98

response:
{
    "errcode": 0,
    "data": {
        "id": "e4c428c0744a484c9555587ae1b22c66",
        "createDate": "2017-04-16 15:19:54",
        "updateDate": "2017-04-16 15:19:54",
        "delFlag": "0",
        "patient": {
            "id": "aeb22b0657b94709a7746d3c599e5c98",
            "remarks": "",
            "createDate": "2017-04-16 12:54:54",
            "updateDate": "2017-04-16 12:54:54",
            "delFlag": "0",
            "loginName": "18610245972",
            "password": "e10adc3949ba59abbe56e057f20f883e",
            "name": "患者1",
            "head": "https://imgsa.baidu.com/baike/w%3D268/sign=ee8fc6544bfbfbeddc59317940f0f78e/8601a18b87d6277f2a0f655328381f30e924fcb8.jpg",
            "sex": 1,
            "birthday": "2017-01-01",
            "mobile": "18610245972",
            "registerIp": "2017-04-16 12:54:51.0",
            "registerTime": "2017-04-16 12:54:51",
            "loginIp": "192.168.1.112",
            "loginDate": "2017-04-16 12:56:31",
            "token": "65f35e28b3c1418eb97a80bbe5d7fc59",
            "type": 2
        },
        "registerCode": "532961",
        "status": 0
    },
    "errmsg": "处理成功"
}
```

### 获取挂号列表信息

```
curl -i -H 'content-type: application/json' -X GET http://192.168.1.112:8080/encounter\?type\=2

{
    "errcode": 0,
    "data": [
        {
            "id": "e4c428c0744a484c9555587ae1b22c66",
            "remarks": "",
            "createDate": "2017-04-16 15:19:55",
            "updateDate": "2017-04-16 15:19:55",
            "delFlag": "0",
            "patient": {
                "id": "aeb22b0657b94709a7746d3c599e5c98",
                "remarks": "",
                "createDate": "2017-04-16 12:54:54",
                "updateDate": "2017-04-16 12:54:54",
                "delFlag": "0",
                "loginName": "18610245972",
                "password": "e10adc3949ba59abbe56e057f20f883e",
                "name": "患者1",
                "head": "https://imgsa.baidu.com/baike/w%3D268/sign=ee8fc6544bfbfbeddc59317940f0f78e/8601a18b87d6277f2a0f655328381f30e924fcb8.jpg",
                "sex": 1,
                "birthday": "2017-01-01",
                "mobile": "18610245972",
                "registerIp": "2017-04-16 12:54:51.0",
                "registerTime": "2017-04-16 12:54:51",
                "loginIp": "192.168.1.112",
                "loginDate": "2017-04-16 12:56:31",
                "token": "65f35e28b3c1418eb97a80bbe5d7fc59",
                "type": 2
            },
            "registerCode": "532961",
            "description": "",
            "status": 0
        }
    ],
    "errmsg": "处理成功"
}

```