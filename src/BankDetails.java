public class BankDetails {

    private int bankId;
    private int branchId;
    private int bankAccountNumber;

    public BankDetails(int bankId) {
        this.bankId = bankId;
    }

    public int getBankId() {
        return bankId;
    }

    public int getBranchId() {
        return branchId;
    }

    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

}
