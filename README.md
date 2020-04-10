V1.0.0版本
本Demo主要目的是使用gitub/gitee(码云) 官方提供的Api接口  实现公共仓库中的文件下载功能
其中Demo提供的url为本人github/gitee的公共仓库  如果需要下载自己仓库中的文件 请将请求的url替换成自己仓库的url

 https://gitee.com/api/v5/repos/GodUcd/smartBandFirmwareVersion/git/trees/master?access_token=f2f24e1a662c823f989223d3116f30f3&recursive=1
 此API根据https://gitee.com/api/v5/swagger#/getV5ReposOwnerRepoGitTreesSha文档获得
 如果是公共仓库access_token=f2f24e1a662c823f989223d3116f30f3参数不需要 仅在文档测试json获取时需要
 
 github也是一样查看APi文档获取公共仓库的Api
 
***注意： 在运行时 请注释access_token=f2f24e1a662c823f989223d3116f30f3&此处    不然会报401提示Token过期
 
