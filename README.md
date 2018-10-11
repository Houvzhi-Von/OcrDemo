> 代码仅仅是调用部分的主要代码，其他可以参考阿里云OCR和腾讯云OCR的API,若有疑问可联系我，邮箱：13718913664@163.com

&nbsp;&nbsp;&nbsp;&nbsp;本次OCR技术方案的选型有两个，一种为阿里云生态服务中的OCR识别API（后文指代为：A），该API为北京艾特阿尔法科技公司提供AI识别服务【此为第三方服务提供商，并非阿里官方服务】；第二种为腾讯云OCR文字识别服务（后文指代为：B），该服务为腾讯提供的官方服务。
### 调用方式
&nbsp;&nbsp;&nbsp;&nbsp;两种服务的调用都需要一个签名验证的步骤，方式大同小异，以下为签名授权的具体方式：
A服务需要在阿里云官网创建应用获取AppId,AppKey，AppSecret等信息进行和A服务提供方进行API的捆绑授权，授权成功后会提供一个AppCode，在后续的识别请求一并作为参数传过去即可。<br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;B服务是通过应用的AppId，SecretKey等字段信息执行一些加密的方法返回一个sign，在后面需要请求API识别时将sign带上去作为请求签名。<br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;A服务的签名授权是由服务提供方自动验证提供给开发者AppCode，而B服务需要开发者自行调用实现sign生成方法。<br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;A服务识别图片时，图片来源是将图片文件通过Base64的方法进行加密作为一个参数请求识别的API，响应的识别结果仅为图片内的文字内容，如图下：<br/>

![阿里云第三方OCR响应数据demo](https://upload-images.jianshu.io/upload_images/2534116-14a5009112e5df22.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
&nbsp;&nbsp;&nbsp;&nbsp;A服务仅支持文件的形式识别，也就是说无法识别网址链接类型的图片，若想识别此类图片需要代码读取下载转为二进制流写入File再进行Base64加密、识别。<br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;B服务在A服务支持的识别方式之上是支持网址链接形式的图片识别，只需要将图片链接地址作为参数一并提交即可识别图片内的文本内容。B服务的识别结果不仅仅有图片中的文本内容，同时还会输出文本你在图片内的位置。（通过X，Y坐标体现，文本的宽度【width】等信息）<br/>

![腾讯云OCR响应数据demo](https://upload-images.jianshu.io/upload_images/2534116-d52279af94da2824.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 稳定性

&nbsp;&nbsp;&nbsp;&nbsp;A服务不稳定，有时会无法获取到响应结果。<br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;B服务测试中一直稳定，可以获取到响应结果。

### 识别率

&nbsp;&nbsp;&nbsp;&nbsp;A服务在电脑截图、电脑复制图片、手机拍摄印刷体图片识别率高，手写字拍摄后识别识别一般。<br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;B服务电脑截图、电脑复制图片、手机拍摄印刷体图片以及手写字体拍摄识别率均是很高。


### 服务价格
##### A服务
![阿里云第三方OCR服务价格](https://upload-images.jianshu.io/upload_images/2534116-7a8ee69390e5e587.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
##### B服务
![腾讯云OCR服务价格](https://upload-images.jianshu.io/upload_images/2534116-0a4255cf328e976e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 综合对比
##### 推荐B服务【腾讯云OCR文字识别】，稳定识别率高，且支持图片链接识别。