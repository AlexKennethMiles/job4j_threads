package ru.job4j.linked;

/**
 * Создали immutable класс: все поля private final, нет сеттеров,
 * и класс не может быть унаследован (модификатор final class).
 * Если бы были поля с ссылочными объектами, их надо было бы возвращать через копии.
 */
public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(final Node<T> next, final T value) {
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
