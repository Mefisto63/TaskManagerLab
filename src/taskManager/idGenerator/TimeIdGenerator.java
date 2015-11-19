package taskManager.idGenerator;

import java.util.Date;
import java.util.Random;

/**
 * Генерирует уникальный ID основываясь на времени создания объекта
 */
public class TimeIdGenerator implements IdGenerator {

    /** Возвращает сгенерированный ID */
    public long generateId() {
        return new Date().getTime() + new Random().nextInt(100);
    }
}
