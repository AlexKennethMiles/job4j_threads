package ru.job4j.linked;

/**
 * Клас стал потокобезопасным за счёт финализации полей класса и ликвидации сеттеров.
 * Состояние объекта класса определяется единожды на этапе создания через конструктор
 * и не может быть изменено далее. Использоваться может только на чтение.
 * Так же класс не может быть унаследован, чтобы методы невозможно было переопределить
 * в классах-наследниках.
 *
 * @param <T> - тип объекта для узла (ноды)
 */
public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}
