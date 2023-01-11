# ComposeRefreshLayout
这是一个简单易用的下拉刷新库，参考[官方Demo](https://github.com/android/compose-samples "官方Demo")进行封装。代码简单易用，可自定义刷新头与上拉加载View。
## 效果展示
![baidu](https://github.com/xiaoyu00/ComposeRefreshLayout/blob/master/%E5%9B%BE%E7%89%87/xg.gif "效果")
## 说明
因刷新头与上拉加载View全部为自定义,比较灵活，所以本库里不包含默认刷新头与加载View，效果中的刷新头与加载View代码在app目录下的simple里
## 使用
### 引入
```
  allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
 }
 ```
```
dependencies {
	   implementation 'com.github.xiaoyu00:ComposeRefreshLayout:1.0.0'
	}
```
### 下拉刷新
```
var refreshing by remember { mutableStateOf(false) }
val scope = rememberCoroutineScope()
RefreshLayout(refreshingState = refreshing, onRefresh = {
            refreshing = true
            scope.launch {
                delay(2000)
                refreshing = false
            }
        }, refreshHeader = { offset, state ->
           // RefreshDefaultHeader(state, offset)
	   // RefreshLottieHeader(state)
           // 你的刷新Header(上面刷新头代码在app下simple里)
        }) {
            // 刷新内容
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                items(30) {
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .padding(start = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Hello $it!")
                    }
                    Divider()
              }
         }
    }
```
### 上拉加载
```
var refreshing by remember { mutableStateOf(false) }
var isLoadMore by remember { mutableStateOf(false) }
val isNoMore = false
val scope = rememberCoroutineScope()
LoadMoreLayout(loadMoreState = isLoadMore, onLoadMore = {
        isLoadMore = true
        scope.launch {
            delay(2000)
            isLoadMore = false
        }
    }, loadMore = { offset, state ->
        // LoadMoreDefaultView(state, isNoMore)(此代码在app下simple里)
	// 你的加载View
    }) {
        RefreshLayout(refreshingState = refreshing, onRefresh = {
            refreshing = true
            scope.launch {
                delay(2000)
                refreshing = false
            }
        }, refreshHeader = { offset, state ->
            //RefreshDefaultHeader(state, offset) (此刷新头代码在app下simple里)
	    // 你的刷新Header
        }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                items(30) {
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .padding(start = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Hello $it!")
                    }
                    Divider()
                }
            }
        }
    }
```
## 属性介绍
### RefreshLoayout
* refreshingState：下拉刷新状态
* refreshEffectHeight：下拉刷新Header高度
* onRefresh：下拉刷新回调
* refreshHeader：下拉刷新Header Composable
* content：刷新内容 Composable
### LoadMoreLoayout
* loadMoreState：上拉状态
* loadMoreEffectHeight：上拉view高度
* onLoadMore：加载回调
* loadMore：加载view Composable
* content：加载内容 Composable


