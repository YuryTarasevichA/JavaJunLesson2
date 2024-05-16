package ru.gb;

import java.lang.reflect.Field;
import java.util.Date;

public class Homework {

    /**
     *  1. Создать аннотацию RandomDate со следующими возможностями:
     *  1.1 Если параметры не заданы, то в поле должна вставляться рандомная дата в диапазоне min, max.
     *  1.2 Аннотация должна работать с полем типа java.util.Date.
     *  1.3 Должна генерить дату в диапазоне [min, max)
     *  1.4 ** Научиться работать с полями LocalDateTime, LocalDate, Instant, ... (классы java.time.*)
     *
     *  Реализовать класс RandomDateProcessor по аналогии с RandomIntegerProcessor, который обрабатывает аннотацию.
     */

    private static @interface RandomDate {

        // UNIX timestamp - количество милисекунд, прошедших с 1 января 1970 года по UTC-0
        long min() default 1704056400L; // 1 января 2024 UTC-3

        long max() default 1735592400L;

    }
    public static class RandomDateProcessor {
        public static void processAnnotations(Object obj) throws IllegalAccessException {
            Class<?> clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(RandomDate.class) && field.getType().equals(Date.class)) {
                    RandomDate annotation = field.getAnnotation(RandomDate.class);
                    long min = annotation.min();
                    long max = annotation.max();
                    long randomTimestamp = getRandomTimestampInRange(min, max);
                    Date randomDate = new Date(randomTimestamp);
                    field.setAccessible(true);
                    field.set(obj, randomDate);
                }
            }
        }

        private static long getRandomTimestampInRange(long min, long max) {
            return min + (long) (Math.random() * (max - min));
        }

    }

}
