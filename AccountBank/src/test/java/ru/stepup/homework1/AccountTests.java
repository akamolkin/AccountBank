package ru.stepup.homework1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class AccountTests {

    @Test
    void testSetOwnerName() {
        Account acc = new Account("Алексеев Василий Ильи");
        String ownerName = acc.getOwnerName();
        acc.setOwnerName(ownerName);
        Assertions.assertEquals(ownerName, acc.getOwnerName());
    }
    @Test
    void testSetWrongOwnerName() {
        Account acc = new Account("Алексеев Василий Ильи");
        Assertions.assertThrows(IllegalArgumentException.class, () -> acc.setOwnerName(""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> acc.setOwnerName(null));
    }

    @Test
    void testSetCurrency() {
        Account acc = new Account("Алексеев Василий Ильи");
        HashMap<CurrencyType, Integer> currencies = new HashMap<>();
        currencies.put(CurrencyType.RUB, 100);
        currencies.put(CurrencyType.EUR, 200);
        currencies.put(CurrencyType.USD, 300);
        acc.setCurrency(CurrencyType.RUB, 100);
        acc.setCurrency(CurrencyType.EUR, 200);
        acc.setCurrency(CurrencyType.USD, 300);
        Assertions.assertEquals(currencies, acc.getCurrencies());
    }

    @Test
    void testSetWrongCurrency() {
        Account acc = new Account("Алексеев Василий Ильи");
        Assertions.assertThrows(IllegalArgumentException.class, () -> acc.setCurrency(CurrencyType.RUB, -100));
        Assertions.assertThrows(IllegalArgumentException.class, () -> acc.setCurrency(CurrencyType.EUR, -200));
        Assertions.assertThrows(IllegalArgumentException.class, () -> acc.setCurrency(CurrencyType.USD, -300));
    }

    @Test
    void testUndo() throws NothingToUndo {
        Account acc = new Account("Алексеев Василий Ильи");
        String oldOwnerName = acc.getOwnerName();
        acc.setCurrency(CurrencyType.RUB, 100);
        acc.setOwnerName("Иванов Петр Васильевич");
        acc.undo();
        Assertions.assertEquals(oldOwnerName, acc.getOwnerName());

        HashMap<CurrencyType, Integer> currencies = new HashMap<>();
        currencies.put(CurrencyType.RUB, 100);
        acc.setCurrency(CurrencyType.RUB, 555);
        acc.undo();
        Assertions.assertEquals(currencies, acc.getCurrencies());
    }

    @Test
    void testSaveLoad() {
        Account acc = new Account("Алексеев Василий Ильи");
        acc.setCurrency(CurrencyType.RUB, 100);
        acc.setCurrency(CurrencyType.EUR, 200);
        Loadable qs = acc.Save();

        String oldOwnerName = acc.getOwnerName();
        acc.setOwnerName("Иванов Петр Васильевич");

        HashMap<CurrencyType, Integer> currencies = new HashMap<>();
        currencies.put(CurrencyType.RUB, 100);
        currencies.put(CurrencyType.EUR, 200);

        qs.load();
        Assertions.assertEquals(oldOwnerName, acc.getOwnerName());
        Assertions.assertEquals(currencies, acc.getCurrencies());

    }

}
