package ru.stepup.homework1;

public class Main {
    public static void main(String[] args) throws NothingToUndo{
        Account acc = new Account("Петров Иван Сергеевич");
        acc.setCurrency(CurrencyType.RUB, 230);
        acc.setCurrency(CurrencyType.EUR, 310);
        System.out.println(acc.toString());

        System.out.println("----undo----");
        acc.undo();
        System.out.println(acc.toString());
        acc.setOwnerName("Иванов Петр Васильевич");
        acc.setCurrency(CurrencyType.RUB, 500);
        System.out.println(acc.toString());

        acc.undo().undo();
        System.out.println(acc.toString());

        System.out.println("----save----");
        Account acc1 = new Account("Алексеев Василий Ильич");
        acc1.setCurrency(CurrencyType.RUB, 750);
        acc1.setCurrency(CurrencyType.EUR, 500);
        System.out.println(acc1.toString());
        Loadable qs = acc1.Save();

        System.out.println("----befor load----");
        acc1.setOwnerName("Васильев Илья Алексеевич");
        acc1.setCurrency(CurrencyType.USD, 200);
        acc1.setCurrency(CurrencyType.RUB, 1000);
        System.out.println(acc1.toString());

        System.out.println("----load----");
        qs.load();
        System.out.println(acc1.toString());
    }
}
