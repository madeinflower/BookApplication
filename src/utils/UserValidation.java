package utils;


public class UserValidation {

    public static boolean isEmailValid(String email) {

        if (email == null) return false;

        // 1. Должна присутствовать @
        int indexAt = email.indexOf('@');
        int lastAt = email.lastIndexOf('@');
        if (indexAt == -1 || indexAt != lastAt) return false;

        // 2. Точка после собаки
        int dotIndexAfterAt = email.indexOf('.', indexAt + 1);
        if (dotIndexAfterAt == -1) return false;

        // 3. после последней точки есть 2 или более символов
        int lastDotIndex = email.lastIndexOf('.');
        if (lastDotIndex >= email.length() -2) return false;

        // 4. Алфавит, цифры, '-', '_', '@', '.'
        /*
        Перебираю все символы в строке. Проверяю текущий символ.
        Если нахожу хоть один "не правильный" сразу возвращаю false
         */

        // string.toCharArray() -> char[]
        for (char ch : email.toCharArray()) {


            // проверка символа на правильность
            boolean isPass = Character.isAlphabetic(ch)
                    || Character.isDigit(ch)
                    || ch == '-'
                    || ch == '_'
                    || ch == '.'
                    || ch == '@';

            // Если символ не подходит (isPass = false) возвращаем false
            if (!isPass) return false; // делай что-то, если переменная false
        }

        // 5. До собаки должен быть хотя бы один символ. Т.е. индекс собаки не равен 0
        if (indexAt == 0) return false;

        // 6. Первый символ - должна быть буква. Символ с индексом 0 должен быть буквой
        if (!Character.isLetter(email.charAt(0))) return false;

        // Все проверки пройдены. Email подходит
        return true;
    }

    public static boolean isPasswordValid(String password) {
        if (password == null || password.length() < 8) return false;

        boolean isDigit = false;
        boolean isUpperCase = false;
        boolean isLowerCase = false;
        boolean isSpecialSymbol = false;

        // альтернативный способ объявления переменных
        boolean[] result = new boolean[4]; // false, false, false, false

        String symbols = "!%$@&*()[],.-";

        // Перебираю символы
        for (char ch : password.toCharArray()) {
            if (Character.isDigit(ch)) isDigit = true;
            if (Character.isUpperCase(ch)) isUpperCase = true;
            if (Character.isLowerCase(ch)) isLowerCase = true;
            if (symbols.indexOf(ch) >= 0) isSpecialSymbol = true;
//            if (symbols.contains(String.valueOf(ch))) isSpecialSymbol = true;
        }
//        System.out.printf("%s | %s | %s | %s\n", isDigit, isUpperCase, isLowerCase, isSpecialSymbol);

        // Если хотя бы одна из переменных останется в значение false, то весь пароль НЕ будет признан валидным (вернется false)
        return isDigit && isUpperCase && isLowerCase && isSpecialSymbol;

    }


}
