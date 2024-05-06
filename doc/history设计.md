### 类概述

`History`类用于记录和管理一个特定银行账户的所有交易历史。该类提供功能以记录各种交易类型（如存款、取款等），并将这些记录以JSON格式保存在文件系统中。

### 成员变量

1. **accountID** (`String`): 该成员变量存储与此历史记录关联的账户的UUID字符串。它用于标识历史记录文件和确保交易记录与正确的账户关联。

2. **account** (`Account`): 该成员变量持有`Account`类型的对象引用，这是传递给构造函数的账户对象。通过此对象可以访问账户的详细信息，如账户名称、余额等。

3. **transactions** (`List<Map<String, Object>>`): 存储所有交易记录的列表。每个交易记录是一个映射，包含了交易的各种详细属性，如交易类型、金额、日期等。

4. **file** (`File`): 该成员变量表示存储交易记录的文件。文件名基于账户的UUID生成，以确保每个账户的历史记录独立存储。

5. **mapper** (`ObjectMapper`): Jackson库的`ObjectMapper`实例，用于JSON数据的序列化和反序列化。此对象负责将`transactions`列表读写到文件系统。

### 构造方法

- **History(Account account)**: 构造方法接受一个`Account`对象作为参数。该方法初始化类的成员变量，包括从账户对象获取UUID字符串，创建对应的文件对象，并尝试从现有文件加载交易记录到`transactions`列表。如果文件不存在，则开始一个新的交易列表。

### 方法

1. **loadTransactions()**: 此私有方法尝试从账户关联的文件中加载交易记录。如果文件存在，则使用`ObjectMapper`读取文件内容到`transactions`列表。如果出现错误，则打印错误堆栈。

2. **recordTransaction(String type, boolean isCredit, double amount, String description)**: 此公共方法用于记录一个新的交易。它接受交易类型（如"deposit", "withdraw"等）、一个表示资金流向的布尔值（正为存款，负为取款）、交易金额和交易描述。此方法创建一个新的交易记录映射，并将其插入到`transactions`列表的最前面，然后调用`saveTransactions()`方法保存更新。

3. **saveTransactions()**: 此私有方法使用`ObjectMapper`将`transactions`列表序列化为JSON格式，并写入到文件系统。此方法确保所有交易记录持久保存，并在出现IO错误时打印错误堆栈。

### 使用示例

以下是如何使用`History`类记录一个新交易的示例：

```java
Account myAccount = new Account("John Doe", 1000, 0.05, 24);
History history = new History(myAccount);
history.recordTransaction("deposit", true, 200, "Salary deposit");
```

这段代码首先创建一个新的账户，然后为这个账户创建一个`History`对象，并记录一个新的存款交易。

这个文档提供了`History`类的全面描述，旨在帮助开发者快速理解和使用这个类。如果需要进一步的示例或有特定的使用场景，请告诉我。

