## 前端的构建

1. 前端需要让页面***继承自JFrame***
    1. 需要的是继承 然后用this.xxx去修改属性
    2. 目的是可以获得 setVisible dispose等函数
    
2. 让你的前端可以根据你需要的对象自动更新

    1. 如果页面需要多次渲染 写一个函数 读入一个这个页面需要知道的信息 然后重新渲染

        1. 例如 AccountManagerController 中的 updateAccounts 函数 (由于需要加Listener 写到了Controller里面)

    2. 如果页面只需要渲染一次(例如每一个Account页面) 那么可以把这个函数写成构造函数

        1. 例如 Page07_Account 的

            ```java
            public Page07_Account(Account account) {
                    this.currentAccount = account;
                    initializeUI(account);
                }
            ```


3. 页面中的 确实有用的实体按键(按下之后产生跳转的 产生计算的),应该写在成员变量里，可以让外部获取
    1. 例如所有的 Exit，创建账户的页面跳转的按钮 等

## Controller的书写

1. 所有的Controller都需要包含三个成员变量

    1. 一个页面 UI 文件夹下面的东西
    2. 一个背后执行逻辑的部分(例如AccountManger负责 转账 利率 增加 删除卡等功能)
    3. 一个 UIStack (因为不会通过触发调用，所以只能每一个对象都保留一个这个堆栈)

2. Controller应该实现 Page 接口

    1. Controller由于包含***页面***和***逻辑代码*** 因此一个Controller 就可以完全表示一个页面的所有功能，因此我们用Controller表征每一个页面，并将Controller添加到UIStack中。所以Controller应该有一些公共的功能

    2. 假设所有的Controller的页面的成员变量都叫 `page`,则所有的Page接口的实现都是**完全一模一样的**，如下面展示

    3. ```java
            @Override
            public void toggleVisibility() {
                this.page.setVisible(!this.page.isVisible());
            }
        
            @Override
            public void setVisibility(boolean visibility) {
                this.page.setVisible(visibility);
            }
        
            @Override
            public boolean getVisibility() {
                return this.page.isVisible();
            }
        
            @Override
            public void dispose() {
                this.page.dispose();
            }
        ```

3. 在Controller的构造函数里面添加 页面中的按下之后会跳转的按键(已经在成员变量中 只需要getXXX)的Listener

4. 跳转的逻辑就是Exit就是pop当前页面，进入一个新的页面就是先new一个页面，创建一个Controller，然后把Controller 放入Stack中

    ```java
    page.getExitButton().addActionListener(e -> uiStack.pop());
    
    // 创建下一个目录
    page.getCreateAccountButton().addActionListener(
        e -> {
            Window1_ChooseAccountType chooseAccountTypeWindow = new Window1_ChooseAccountType();
            AccountTypeChooseController accountTypeChooseController =
                new AccountTypeChooseController(chooseAccountTypeWindow, accountManager, this.uiStack);
            uiStack.pushWindows(accountTypeChooseController);
        }
    );
    ```

    

## UI Stack的原理与应用

### pop

pop只会弹出当前栈顶的页面，然后摧毁掉栈顶的页面

如果下一个页面是不可见的，那么让其可见。如果本身就是可见的，什么也不做。(即保证当前栈顶的元素永远可见)

### push

1. pushPage
    1. 正常的push一个页面，将原来栈顶的元素改为不可见，新的设置为可见
    2. 就是正常的大页面的切换
2. pushWindow
    1. 保持栈顶的可见，直接加入新的元素
    2. 目的是完成弹窗的处理
3. swapWindow
    1. 弹出栈顶的页面，摧毁这个页面，然后直接push一个新的页面
    2. 实现的效果是替换栈顶的页面
    3. 目的是实现小窗口的推出后，是上一个大的Page

## 具体案例

可以参考案例，其对应的页面在Controller的成员变量中可以看见

1. AccountManagerController 还带有一个更改AccountManager时自动触发页面更新的触发器
2. AccountController 
3. AccountTypeChooseController 
4. CreateSavingAccountController
5. ......





