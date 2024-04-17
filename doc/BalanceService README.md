

### BalanceService 使用说明文档

#### 1. 服务概述
`BalanceService` 提供了一系列方法，用于更新不同类型账户的余额，并记录相关交易信息。此服务支持的账户类型包括活期账户（`CurrentAccount`）、存钱罐（`PiggyBank`）和定期账户（`SavingAccount`）。

#### 2. 方法说明

##### 2.1 `updateBalance(Account account, double amount, String transactionType, String description)`
- **目的**：根据账户类型更新账户的余额，并根据交易类型记录交易日志。
- **参数**：
  - `Account account`：要更新的账户对象，可以是 `CurrentAccount`、`PiggyBank` 或 `SavingAccount`。
  - `double amount`：交易金额。正数表示存入，负数表示取出。
  - `String transactionType`：交易类型，例如 `"consume"`, `"save"`, `"transfer"`, `"interest"`, `"prize"`。
  - `String description`：交易的描述信息，用于日志记录和审计。
- **返回**：更新后的账户对象。
- **使用场景**：当需要对任何支持的账户类型执行存款或取款操作时调用此方法。

##### 2.2 `calculateInterest(CurrentAccount account)`
- **目的**：计算活期账户自上次计息日期以来应获得的利息。
- **参数**：
  - `CurrentAccount account`：要计算利息的活期账户。
- **返回**：计算得出的利息金额。
- **使用场景**：每日结束时或在需要时计算活期账户的利息。

##### 2.3 `calculateMaturityInterest(SavingAccount account)`
- **目的**：计算定期账户到期时的总利息。
- **参数**：
  - `SavingAccount account`：要计算利息的定期账户。
- **返回**：计算得出的到期总利息。
- **使用场景**：定期账户到期时，计算并更新账户的本金和利息总额。

#### 3. 注意事项
- 在调用 `updateBalance` 方法时，如果取款金额大于账户余额，将抛出 `IllegalArgumentException` 异常，提示资金不足。
- 日志记录当前只输出到控制台，根据项目需求，可能需要将其改为写入日志文件或数据库。

#### 4. 使用示例

```java
BalanceService balanceService = new BalanceService();

// 更新活期账户余额
CurrentAccount currentAccount = new CurrentAccount("12345", 1000.00);
balanceService.updateBalance(currentAccount, 200, "save", "Deposit from ATM");

// 计算并更新利息
double interest = balanceService.calculateInterest(currentAccount);
balanceService.updateBalance(currentAccount, interest, "interest", "Daily interest");

// 输出结果
System.out.println("Updated balance: " + currentAccount.getBalance());
```

#### 5. 异常处理
请确保在调用方法时处理 `IllegalArgumentException` 异常，以防止程序因异常而中断执行。

---

