public class Main{
    public static void main(String[] args){
        Account account = new Account();
        Deposit deposit = new Deposit(account);
        Output output = new Output(account);
        new Thread(deposit).start();
        new Thread(output).start();
    }
}

class Account extends Thread{
    private int balance = 0;
    private final int output = 5000;

    public synchronized void toDeposit(){
        while(balance >= output){
            try{
                wait();
            }
            catch (Exception ex){
            }
        }
        balance += 500;
        System.out.println("Баланс пополнен на 500.");
        System.out.println("Баланс сейчас " + balance);
        notify();
    }

    public synchronized void toWithdrawal(){
        while(balance < output){
            try {
                wait();
            }
            catch (Exception ex){

            }
        }
        balance -= 2500;
        System.out.println("С карты списано 2500");
        System.out.println("Баланс сейчас " + balance);
        notify();
    }
}
class Output extends Thread{

    Account account;
    Output(Account account){
        this.account=account;
    }
    public void run(){
        for (int i = 1; i < 50; i++) {
            account.toDeposit();
        }
    }
}
class Deposit extends Thread{

    Account account;
    Deposit(Account account){
        this.account=account;
    }
    public void run(){
        for (int i = 1; i < 50; i++) {
            account.toWithdrawal();
        }
    }
}
