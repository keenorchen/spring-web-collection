# RestTempalate 基本使用

## 目标
- 普通的Get请求获取返回数据，怎么玩？
- post提交表达的请求，如何处理
- post请求中RequestBody的请求方式与普通的请求方式区别
- https/http两种访问如何分别处理
- 如何在请求中带上指定的Header
- 有跨域的问题么？如果有怎么解决
- 有登录验证的请求，该怎么办，怎样携带身份信息
- 上传文件可以支持么
- 对于需要代理才能访问的http资源，加代理的姿势是怎样的

## 基本接口
```
// get 请求
 public <T> T getForObject();
 public <T> ResponseEntity<T> getForEntity();
 
 // head 请求
 public HttpHeaders headForHeaders();
 
 // post 请求
 public URI postForLocation();
 public <T> T postForObject();
 public <T> ResponseEntity<T> postForEntity();
 
 // put 请求
 public void put();
 
 // pathch 
 public <T> T patchForObject
 
 // delete
 public void delete()
 
 // options
 public Set<HttpMethod> optionsForAllow
 
 // exchange
 public <T> ResponseEntity<T> exchange()
```

## Get请求
- 从接口的签名上，可以看出一个是直接返回预期的对象，一个则是将对象包装到 ResponseEntity 封装类中
- 如果只关心返回结果，那么直接用 GetForObject 即可
- 如果除了返回的实体内容之外，还需要获取返回的header等信息，则可以使用 getForEntit

### getForObject方式
```
@Nullable
public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException ;

@Nullable
public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException ;

@Nullable
public <T> T getForObject(URI url, Class<T> responseType) throws RestClientException;
```

### getForEntity方式
多了两个东西:
- 一个返回的http状态码，如200表示请求成功，500服务器错误，404not found等
- 一个 ResponseHeader

```java
public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException ;
public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;
public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) throws RestClientException;
```

## Post请求
三种方式：
- uri参数，使用姿势和get请求中一样，填充uri中模板坑位
- 表单参数，由MultiValueMap封装，同样是kv结构
- json参数，放入 header的request body 中
- 上传文件

## postForLocation
- POST 数据到一个URL，返回新创建资源的URL
- 例如：我们一般登录or注册都是post请求，而这些操作完成之后呢？大部分都是跳转到别的页面去了，这种场景下，就可以使用 postForLocation 了，提交数据，并获取返回的URI


## 请求头设置
### Get请求
1. 自定义一个拦截器，然后在你实际发起请求时，拦截并设置request的请求头
2. RestTemplate 的父类InterceptingHttpAccessor提供了一个接收Interceptor的接口org.springframework.http.client.support.InterceptingHttpAccessor#setInterceptors
3. restTemplate.setInterceptors(Collections.singletonList(new UserAgentInterceptor()));

### Post请求
Form:
- 具体的header信息分装到 HttpHeaders 对象中
- 请求参数依然封装到 MultiValueMap 中
- 然后根据请求头 + 请求参数，构建 HttpEntity 对象，将这个作为post的请求request参数传入

Json:
- headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
- 注意下post参数，是放在Map容器中，而不是之前的MultiValueMap

### exchange 方式
支持泛型传参


