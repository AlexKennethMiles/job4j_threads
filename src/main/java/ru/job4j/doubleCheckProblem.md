### Проблема Double Check 

Оптимизация в _Java_ устроена таким образом, что при изменении
переменной она вместо того чтобы:

![](https://i2.paste.pics/I4RAD.png)

постоянно передаваться из _Heap_'а в _Stack_, изменяться на _Stack_'е
и затем перезаписываться в _Heap_, _Java_ делает так:

![](https://i2.paste.pics/I4RAE.png)

загружает переменную в _Stack_, затем производит сразу все операции и
только затем возвращает готовый объект в _Heap_.
Теперь как это работает для случая с _double check_ в _singleton_:

![](https://i2.paste.pics/I4RB3.png)

Проблема решается тем, что мы блокируем этот механизм оптимизации,
чтобы не возникала ситуация, описанная выше.
Делается это при помощи ключевого слова _**volatile**_.
И тогда, да, метод получения экземпляра _singleton_ будет не оптимизирован,
но нам это и не особо нужно, так как он создаётся только один раз за весь период работы программы.
И тогда ситуация изменится на такую:

![](https://i2.paste.pics/I4RB5.png)