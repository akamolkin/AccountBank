package ru.stepup.homework1;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class Account {
    private String ownerName;
    private HashMap<CurrencyType, Integer> currencies;
    private Deque<Command> commands = new ArrayDeque<>();
    private class Snapshot implements Loadable {
        private String ownerName;
        private HashMap<CurrencyType, Integer> currencies;

        public Snapshot () {
            this.ownerName = Account.this.ownerName;
            this.currencies = new HashMap<>(Account.this.currencies);
        }
        @Override
        public void load() {
            Account.this.ownerName = this.ownerName;
            Account.this.currencies = new HashMap<>(this.currencies);
        }
    }
    private Account() {
    }

    public Account(String ownerName) {
        this.setOwnerName(ownerName);
        this.currencies = new HashMap<>();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        if (ownerName == null || ownerName.isEmpty()) throw new IllegalArgumentException();
        String oldName = this.ownerName;
        this.commands.push(() -> {this.ownerName = oldName;});
        this.ownerName = ownerName;
    }

    public HashMap<CurrencyType, Integer> getCurrencies() {
        return new HashMap<CurrencyType, Integer>(this.currencies);
    }

    public void setCurrency(CurrencyType currencyType, Integer val) {
        if (val < 0) throw new IllegalArgumentException();
        if (currencies.containsKey(currencyType)) {
            Integer oldVal = this.currencies.get(currencyType);
            this.commands.push(() -> {this.currencies.put(currencyType, oldVal);});
        }
        else {
            this.commands.push(()->{this.currencies.remove(currencyType);});
        }
        this.currencies.put(currencyType, val);
    }

    public  Account undo() throws NothingToUndo {
        if (commands.isEmpty()) throw new NothingToUndo();
        commands.pop().perform();
        return this;
    }

    public Loadable Save() {
        return new Snapshot();
    }

    @Override
    public String toString() {
        return "Account{" +
                "ownerName='" + ownerName + '\'' +
                ", currency=" + currencies +
                '}';
    }
}
