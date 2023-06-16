package client.utility;

import client.AppClient;
import common.data.*;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInDeclaredLimitsException;
import common.utility.Outputer;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Asks user a study group's value.
 */
public class StudyGroupAsker {
    private final int MAX_Y = 262;
    private final int MIN_STUDENTS_COUNT = 1;
    private final int MIN_EXPELLED_MARKS = 1;
    private final int MIN_AVERAGE_MARK = 1;
    private final long MIN_X = -438;
    private final long MIN_WEIGHT = 1;
    private Scanner userScanner;
    private boolean fileMode;

    public StudyGroupAsker(Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    /**
     * Sets marine asker mode to 'File Mode'.
     */
    public void setFileMode() {
        fileMode = true;
    }

    /**
     * Sets marine asker mode to 'User Mode'.
     */
    public void setUserMode() {
        fileMode = false;
    }

    /**
     * Asks a user the group's name.
     *
     * @return Group's name.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                Outputer.println("Введите имя:");
                Outputer.print(AppClient.PS2);
                name = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Имя не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return name;
    }

    /**
     * Asks a user the Group's X coordinate.
     * @return Group's X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askX() throws IncorrectInputInScriptException {
        String strX;
        long x;
        while (true) {
            try {
                Outputer.println("Введите координату X:");
                Outputer.print(AppClient.PS2);
                strX = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strX);
                x = Long.parseLong(strX);
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Координата X не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Координата X должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Asks a user the Group's Y coordinate.
     * @return Group's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askY() throws IncorrectInputInScriptException {
        String strY;
        long y;
        while (true) {
            try {
                Outputer.println("Введите координату Y < " + (MAX_Y+1) + ":");
                Outputer.print(AppClient.PS2);
                strY = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strY);
                y = Long.parseLong(strY);
                if (y > MAX_Y) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Координата Y не может превышать " + MAX_Y + "!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * Asks a user the Group's coordinates.
     * @return Group's coordinates.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        long x;
        long y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }

    /**
     * Asks a user the studentsCount .
     * @return studentsCount.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public int askStudentsCount() throws IncorrectInputInScriptException {
        String strStudentsCount;
        int studentsCount;
        while (true) {
            try {
                Outputer.println("Введите количество студентов:");
                Outputer.print(AppClient.PS2);
                strStudentsCount = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strStudentsCount);
                studentsCount = Integer.parseInt(strStudentsCount);
                if (studentsCount < MIN_STUDENTS_COUNT) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Количество студентов не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Количество студентов должно быть больше нуля!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Количество студентов должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return studentsCount;
    }

    /**
     * Asks a user the expelledStudents.
     * @return expelledStudents.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public int askExpelledStudents() throws IncorrectInputInScriptException {
        String strExpelledStudents;
        int expelledStudents;
        while (true) {
            try {
                Outputer.println("Введите количество исключенных студентов:");
                Outputer.print(AppClient.PS2);
                strExpelledStudents = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strExpelledStudents);
                expelledStudents = Integer.parseInt(strExpelledStudents);
                if (expelledStudents < MIN_EXPELLED_MARKS) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Количество исключенных студентов не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Количество исключенных студентов должно быть больше нуля!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Количество исключенных студентов должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return expelledStudents;
    }
    public int askAverageMark() throws IncorrectInputInScriptException {
        String strAverageMark;
        int averageMark;
        while (true) {
            try {
                Outputer.println("Введите средний балл:");
                Outputer.print(AppClient.PS2);
                strAverageMark = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strAverageMark);
                averageMark = Integer.parseInt(strAverageMark);
                if (averageMark < MIN_AVERAGE_MARK) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Средний балл не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Средний балл должен быть больше нуля!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Средний балл должен быть представлен числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return averageMark;
    }

    public FormOfEducation askFormOfEducation () throws IncorrectInputInScriptException {
        String strFormOfEducation ;
        FormOfEducation form;
        while (true) {
            try {
                Outputer.println("Список форм - " + FormOfEducation.nameList());
                Outputer.println("Введите форму обучения:");
                Outputer.print(AppClient.PS2);
                strFormOfEducation = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strFormOfEducation);
                form = FormOfEducation.valueOf(strFormOfEducation.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Форма не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("Формы нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return form;
    }

    /**
     * Asks a user the Group's admin .
     * @return Group's admin.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askPersonName() throws IncorrectInputInScriptException {
        String personName;
        String personNamee;
        while (true) {
            try {
                Outputer.println("Введите имя админа:");
                Outputer.print(AppClient.PS2);
                personNamee = userScanner.nextLine().trim();
                personName = personNamee;
                if (fileMode) Outputer.println(personName);
                if (personName.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Поле не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return personName;
    }


    /**
     * Asks a user the Person's weight.
     * @return Person's weight.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askWeight() throws IncorrectInputInScriptException {
        String strWeight;
        long weight;
        while (true) {
            try {
                Outputer.println("Введите вес:");
                Outputer.print(AppClient.PS2);
                strWeight = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strWeight);
                weight = Long.parseLong(strWeight);
                if (weight < MIN_WEIGHT) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Вес не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Вес должен быть положительным числом");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Вес должен быть представлен числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return weight;
    }
    public Color askHairColor() throws IncorrectInputInScriptException {
        Color hairColor;
        while (true) {
            try {
                Outputer.println("Введите цвет волос:");
                Outputer.print(AppClient.PS2);
                hairColor = Color.valueOf(userScanner.next().trim());
                if (fileMode) Outputer.println(hairColor);
                if (hairColor.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Цвет волос не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("Цвет волос не распознан!");
                if (fileMode) throw new IllegalArgumentException();
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Поле не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return hairColor;
    }

    public Country askNationality() throws IncorrectInputInScriptException {
        Country nationality;
        while (true) {
            try {
                Outputer.println("Введите национальность:");
                Outputer.print(AppClient.PS2);
                nationality = Country.valueOf(userScanner.next().trim());
                if (fileMode) Outputer.println(nationality);
                if (nationality.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Национальность не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("Национальность не распознана!");
                if (fileMode) throw new IllegalArgumentException();
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Поле не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return nationality;
    }


    /**
     * Asks a user Person's info.
     * @return Person's info.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Location askLocation() throws IncorrectInputInScriptException {
        Double x = askXLocation(); //Поле не может быть null
        long y = askYLocation();
        Double z = askZLocation(); //Поле не может быть null
        String name = askNameLocation(); //Поле может быть null
        return new Location(x,y,z, name);
    }
    public String askNameLocation() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                Outputer.println("Введите название местоположения:");
                Outputer.print(AppClient.PS2);
                name = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Имя не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return name;
    }
    public Double askXLocation() throws IncorrectInputInScriptException {
        String strXLocation;
        Double x;
        while (true) {
            try {
                Outputer.println("Введите координату X:");
                Outputer.print(AppClient.PS2);
                strXLocation = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strXLocation);
                x = Double.parseDouble(strXLocation);
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Координата X не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Координата X должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }
    public Double askZLocation() throws IncorrectInputInScriptException {
        String strZLocation;
        Double z;
        while (true) {
            try {
                Outputer.println("Введите координату Z:");
                Outputer.print(AppClient.PS2);
                strZLocation = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strZLocation);
                z = Double.parseDouble(strZLocation);
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Координата Z не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Координата Z должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return z;
    }
    /**
     * Asks a user the Group's Y coordinate.
     * @return Group's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askYLocation() throws IncorrectInputInScriptException {
        String strYLocation;
        long y;
        while (true) {
            try {
                Outputer.println("Введите координату Y: ");
                Outputer.print(AppClient.PS2);
                strYLocation = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strYLocation);
                y = Long.parseLong(strYLocation);
                if (y > MAX_Y) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Координата Y не может превышать " + MAX_Y + "!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }


    public Person askPerson() throws IncorrectInputInScriptException {
        String name;
        long weight;
        Color hairColor;
        Country nationality;
        Location location;
        name = askPersonName();
        weight = askWeight();
        hairColor = askHairColor();
        nationality = askNationality();
        location = askLocation();
        return new Person(name, weight,hairColor,nationality, location);
    }
    /**
     * Asks a user a question.
     *
     * @param question A question.
     * @return Answer (true/false).
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public boolean askQuestion(String question) throws IncorrectInputInScriptException {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Outputer.println(finalQuestion);
                Outputer.print(AppClient.PS2);
                answer = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Ответ не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Ответ должен быть представлен знаками '+' или '-'!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return answer.equals("+");
    }
}
