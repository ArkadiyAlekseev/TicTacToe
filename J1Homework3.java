package ru.geekbrains.JavaLevelOne.git;

import java.util.Random;
import java.util.Scanner;

/**
 * Java 1 Lesson 3 Homework
 *
 * @author Alekseev A
 * @ version date 08/10/18
 */
public class J1Homework3 {
    /**
     * @крестики нолики/
     * Исходные данные:
     * поле координат (26 строка)
     * метод ввода координат
     * х-0 (21-23 строки)
     * игроки или ии
     * проверка пустоты попадания в поле
     * проверка конца игры
     * вывод поля на экран
     */
    private static final char PLAYER_DOT = 'X';//обозначение для точки человека
    private static final char COMPUTER_DOT = 'O';//обозначение для точки ИИ
    private static final char EMPTY_DOT = '_';//проверка пустоты+разметка полей снизу
    private static final Scanner SCANNER = new Scanner(System.in);//объект сканнера для считывания пользовательского ввода
    private static final Random RANDOM = new Random();//генератор случайных чисел
    private static int fieldSizeX;// разметка поля
    private static int fieldSizeY;// разметка поля
    private static char[][] field;//поле координат
    private static int victoryLine = 4;//сколько нужно для победы в игре

    private static String repeat() {//ЭТАП 1. Возвращающий вводимое пользователем значение метод для рестарта
        System.out.print("\n" + "To restart the game, enter y: ");
        return SCANNER.next();
    }
    private static void fieldСreation() {//ЭТАП 2. Инициализация поля
        fieldSizeX = 5;
        fieldSizeY = 5;
        field = new char[fieldSizeX][fieldSizeY];
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = EMPTY_DOT;//разметит поля снизу
            }
        }
    }
    private static void fieldPrint() {// ЭТАП 3. Вывод поля на экран
        System.out.println("_y_y_y_");//оси координат Y
        for (int y = 0; y < fieldSizeY; y++) {
            System.out.print("x");//оси координат X
            for (int x = 0; x < fieldSizeX; x++) {
                System.out.print(field[y][x] + "|");//разметка полей сбоку
            }
            System.out.println();//перенос строки
        }
    }

    private static boolean correctCell(int x, int y) {//попадает ли ячейка в диапазон координат
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    private static boolean emptyCell(int x, int y) {//является ли ячейка пустой
        return field[y][x] == EMPTY_DOT;
    }

    private static void playerMove() {//ЭТАП 4. Ввод и проверка координат координат
        int x;
        int y;
        do {//цикличное введение координат до выполнения условия
            System.out.print("Please enter coordinates X and Y (1 to 3) separated by space: ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        }
        while (!emptyCell(x, y) || !correctCell(x, y));//проверка на пустоту ячейки и проверка, что координаты введены в поле
        field[y][x] = PLAYER_DOT;
    }

    private static boolean victoryCheck(char c) {// ЭТАП 5. Метод проверки победы
        int lineLength = 0;//заполненные ячейки до победы
        int y;
        int x;
        for (y = 0; y < fieldSizeY; y++) {//проверка по горизонтали
            lineLength = 0;
            for (x = 0; x < fieldSizeX; x++) {
                if (field[y][x] != c) lineLength = 0;
                else lineLength++;
                if (lineLength == victoryLine) return true;
            }
        }
        for (x = 0; x < fieldSizeX; x++) {//проверка по вертикали
            lineLength = 0;
            for (y = 0; y < fieldSizeY; y++) {
                if (field[y][x] != c) lineLength = 0;
                else lineLength++;
                if (lineLength == victoryLine) return true;
            }
        }
        // проверка по диагонали внутри треугольника с катетами внизу и слева (+\)
        for (int a = 0; a < fieldSizeY - victoryLine; a++) {
            lineLength = 0;
            for (y = fieldSizeY - victoryLine - a, x = 0; y >= 0 && y < fieldSizeY && x < fieldSizeX; y++, x++) {
                if (field[y][x] != c) lineLength = 0;
                else lineLength++;
                if (lineLength == victoryLine) return true;
            }
        }
        // проверка по диагонали внутри треугольника с катетами сверху и справа(\+)
        for (int a = 0; a < fieldSizeY - victoryLine; a++) {
            lineLength = 0;
            for (x = fieldSizeX - victoryLine - a, y = 0; x >= 0 && x < fieldSizeX && y < fieldSizeY; y++, x++) {
                if (field[y][x] != c) lineLength = 0;
                else lineLength++;
                if (lineLength == victoryLine) return true;
            }
        }
        // проверка по диагонали внутри треугольника с катетами внизу справа(/+)
        for (int a = 0; a < fieldSizeY - victoryLine; a++) {
            lineLength = 0;
            for (y = fieldSizeY - victoryLine - a, x = fieldSizeX - 1; y >= 0 && y < fieldSizeY && x >= 0; y++, x--) {
                if (field[y][x] != c) lineLength = 0;
                else lineLength++;
                if (lineLength == victoryLine) return true;
            }
        }
        // проверка по диагонали внутри треугольника с катетами сверху и слева (+/)
        for (int a = 0; a < fieldSizeY - victoryLine; a++) {
            lineLength = 0;
            for (x = fieldSizeX - victoryLine - a, y = 0; x >= 0 && x < fieldSizeX && y >= 0; x--, y++) {
                if (field[y][x] != c) lineLength = 0;
                else lineLength++;
                if (lineLength == victoryLine) return true;
            }
        }
        return false;
    }

    private static boolean drawCheck() {//ЭТАП 6. метод проверки ничьей
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (emptyCell(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
    private static void computerMove() {// ЭТАП 7. ход компьютера - TODO ЗАМЕНИТЬ НА БОЛЕЕ УМНОЕ ПОВЕДЕНИЕ
        int x;//инициализация внутри метода
        int y;
        do {
            x = RANDOM.nextInt(fieldSizeX);//рандомное значение в пределах поля
            y = RANDOM.nextInt(fieldSizeY);
        } while (!emptyCell(x, y));//проверка с пустым полем
        field[y][x] = COMPUTER_DOT;//запись
    }


    public static void main(String[] args) {
        while (true) {// запрос на повтор игры
            fieldСreation();//инциализируем поле
            fieldPrint();//выводим поле на экран
            while (true) {//процесс игры
                playerMove();//игрок делает первый шаг
                fieldPrint();//печать поля с ходом игрока
                if (victoryCheck(PLAYER_DOT)) {
                    System.out.println("Player wins!");
                    break;
                }
                if (drawCheck()) {
                    System.out.println("Draw!");
                    break;
                }
                computerMove();
                fieldPrint();//печать поля с ходом Компьютера
                if (victoryCheck(COMPUTER_DOT)) {
                    System.out.println("Computer wins!");
                    break;
                }
                if (drawCheck()) {
                    System.out.println("Draw!");
                    break;
                }
            }
            if (!repeat().equals("y")) break;
        }
    }
}

