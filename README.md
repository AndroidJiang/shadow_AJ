



###  备忘录模块

腾讯shadow插件化

6.6-6.7  泛型

6.9 注解





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(818)



### **Java**

**java基础看享学2**

Java实现多态有 3 个必要条件：继承、重写和向上转型



#### **集合**

核心两个集合 增删改查细节

##### **ArrayList**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image1.png" alt="img" style="zoom:80%;" /> 

**扩容机制**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image2.png" alt="img" style="zoom:80%;" /> 

总结：无参构造器，初始容量10，以后扩容1.5倍

​			有参构造器，1.5倍

特点：数组连续，方便查询，插入删除慢，要移动数据

增删改查

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image3.png" alt="img" style="zoom:80%;" /> 

**Vector**

线程安全，初始10，后面2倍扩容

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image4.png" alt="img" style="zoom:80%;" /> 



**LinkedList** 双向链表

构造函数

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image5.png" alt="img" style="zoom:80%;" /> 

\1. add 第一个

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image6.png" alt="img" style="zoom:80%;" /> 

add第二个

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image7.png" alt="img" style="zoom:80%;" /> 

removeFirst

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image8.png" alt="img" style="zoom:80%;" /> 



注意点：

List批量删除 直接for int i 会导致结果不准确  增强for其实就是迭代器 list.remove会导致并发异常 见链接[ArrayList边遍历边删除？ - 云+社区 - 腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/1556999)

原因：迭代器内部的每次遍历都会记录List内部的modcount当做预期值，然后在每次循环中用预期值与List的成员变量modCount作比较，但是普通list.remove调用的是List的remove，这时modcount++，但是iterator内记录的预期值=并没有变化，所以会报错,但是如果在Iterator中调用remove，这时会同步List的modCount到Iterator中，故不再报错



**HashSet**

底层hashmap，元素可以为null 但不能重复，根据元素的hashcode进行排序

public boolean add(Object o)方法用来在Set中添加元素，当元素值重复时则会立即返回false，如果成功添加的话会返回true



<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image9.png" alt="img" style="zoom:80%;" /> 









##### **HashMap**

数组插入删除比较麻烦，链表遍历死了，结合两者优点

######  **put源码**

1.7分析

l hash（key）

l indexfor(hash,length) 得出下标  hash & (length-1)

l for  遍历链表 一样则替换value

l addEntry  头插法

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image10.png" alt="img" style="zoom: 67%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image11.png" alt="img" style="zoom: 67%;" /> 



1.8分析

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image12.png" alt="img" style="zoom: 67%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image13.png" alt="img" style="zoom: 67%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image14.png" alt="img" style="zoom: 50%;" /> 

插入流程![image-20220922092523423](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20220922092523423.png)

###### **扩容**

红黑树机制

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image15.png" alt="img" style="zoom:50%;" /> 

**resize源码分析**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image16.png" alt="img" style="zoom:50%;" /> 

###### get源码

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image17.png" alt="img" style="zoom:80%;" /> 

疑问：

l 无链表16倒32裸数组是如何进行迁移的

l 有链表16倒32如何迁移





###### **面试题**

**为什么重写equals方法，还必须要重写hashcode方法？重写的规则是啥**

> 1.提高效率。hash类型的存储结构，添加元素重复性校验的标准就是先取hashCode值，后判断equals()。重写后，使用hashcode方法提前校验，可以避免每一次比对都调用equals方法。
>
> 2.保证是同一个对象。如果重写了equals方法，而没有重写hashcode方法，会出现equals相等的对象，hashcode不相等的情况，重写hashcode方法就是为了避免这种情况的出现。
>
> 规则：
> 1、是否两个equal的实例，拥有相同的hashCode
> 2、两个不同的实例，是否拥有相同的hashCode

**hash扰动函数解释**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image18.png" alt="img" style="zoom:80%;" /> 

**数组长度为啥是2的mi/扩容2倍**

> hash & (length-1)  length-1刚好某个位置后面全是1，然后&，就算hash很大，也会分配到很小的index上
>
> 1.8数组扩容，index要么在原位置，要么在i+oldlength，因为数组扩容后，newlength-1二进制比oldlength-1最高位多1，如1111-》11111，这样和hash&后，取决于hash的最高位是否为1，如果为1，则index=i+oldlength,否则在原位置i。如15的二进制后四位为1111，这样与1或0进行与运算时，得到的结果可能为1或0，不单单为1或0，减少hash碰撞，使元素分配均匀；如17  length-1 = 16，后四位0000，那么与运算结果固定为0。
>
>

**Hash冲突如何解决：**

> 1. 开放地址法 也叫 线性探测法，当前位置车冲突，找下个位置，参考ThreaLocal
>
> 2. 链地址法，1.7的hashmap，1.8是链地址法+红黑树
>
> 3. Rehash，冲突了在进行一次hash算法
>
> 4. 两个表，冲突的单独放个表

**负载因子0.75是啥**

> 考虑空间和时间效率平衡的一个选择

**为什么链表长度达到8作为红黑树条件**

> 当长度为 8 的时候，概率仅为 0.00000006，这是一个小于千万分之一的概率，通常我们的 Map 里面是不会存储这么多的数据的，所以通常情况下，并不会发生从链表向红黑树的转换，链表长度超过 8 就转为红黑树的设计，更多的是为了防止用户自己实现了不好的哈希算法时导致链表过长，从而导致查询效率低，而此时转为红黑树更多的是一种保底策略，用来保证极端情况下查询的效率

**为什么退化为链表的阈值是6**

> 上面说到，当链表长度达到阈值8的时候会转为红黑树，但是红黑树退化为链表的阈值却是6，为什么不是小于8就退化呢？比如说7的时候就退化，偏偏要小于或等于6？主要是一个**过渡**，避免链表和红黑树之间频繁的转换。如果阈值是7的话，删除一个元素红黑树就必须退化为链表，增加一个元素就必须树化，来回不断的转换结构无疑会降低性能，所以阈值才不设置的那么临界

**HashMap中put是如何实现的**

> 1. 判断数组是否为空，为空进行初始化;
> 2. 不为空，计算 k 的 hash 值，通过(n - 1) & hash计算应当存放在数组中的下标 index;
> 3. 查看 table[index] 是否存在数据，没有数据就构造一个Node节点存放在 table[index] 中；
> 4. 存在数据，说明发生了hash冲突(存在二个节点key的hash值一样), 继续判断key是否相等，相等，用新的value替换原数据(onlyIfAbsent为false)；
> 5. 如果不相等，判断当前节点类型是不是树型节点，如果是树型节点，创造树型节点插入红黑树中；(如果当前节点是树型节点证明当前已经是红黑树了)
> 6. 如果不是树型节点，创建普通Node加入链表中；判断链表长度是否大于8并且数组长度大于64，大于的话链表转换为红黑树；
> 7. 插入完成之后判断当前节点数是否大于阈值（capacity*loadFactor），如果大于开始扩容为原数组的二倍。

**谈一下hashMap中什么时候需要进行扩容，扩容resize()又是如何实现的？**

> 1.第一次添加元素时，即当HashMap的数组为null或者数组的长度为0时；
>
> ```
> final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
>             boolean evict) {
>  Node<K,V>[] tab; Node<K,V> p; int n, i;
>  if ((tab = table) == null || (n = tab.length) == 0)
>      n = (tab = resize()).length;
> ```
>
> 2.链表转红黑树、且数组**容量**（length）小于64时；
>
> ```
> final void treeifyBin(Node<K,V>[] tab, int hash) {
>  int n, index; Node<K,V> e;
>  if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
>      resize();
> ```
>
> 3.数组容量(hashmap的size，元素个数)大于扩容阈值时；
>
> ```
> if (++size > threshold)
>  resize();
> ```
>
> 见上resize分析

**HashMap和HashTable的区别 小米**

> HashMap是非线程安全的，Hashtable是线程安全的
>
> HashMap允许null作为键或值，Hashtable不允许,运行时会报NullPointerException
>
> HashMap添加元素使用的是自定义hash算法，Hashtable使用的是key的hashCode

**Arraylist和HashMap的区别，前者为什么取数快？**

> 前者数组，可重复，适合快速访问查询，后者数组+链表+红黑树，不可重复，适合插入删除
>
> 因为ArrayList底层是数组实现的,根据下标查询不需要比较,查询方式为,首地址+(元素长度*下标),基于这个位置读取相应的字节数就可以了,所以非常快;增删会带来元素的移动,增加数据会向后移动,删除数据会向前移动,所以影响效率。

**1.7和1.8扩容的区别**

> 1.7新建扩容二倍的数组，遍历老数组进行rehash到新数组中，长度改变，规则也变，**先扩容再插值**
>
> 1.8要么原位置i，要么oldlength+i，**先插入再扩容**

**为什么在 JDK1.7 的时候是先进行扩容后进行插入，而在 jdk1.8 的时候是先插入再进行扩容呢？**

>

**1.7和1.8区别**

> 底层数据结构：1.7 数组+链表  1.8 数组+链表+红黑树
>
> 元素插入流程：1.7先扩容再添加，1.8先添加在扩容
>
> 节点类型：1.7中数组中节点类型是Entry节点，1.8中数组中节点类型是Node节点；
>
> 元素插入方式：1.7头插法，1.8尾插法，解决了链表死循环bug（https://blog.csdn.net/littlehaes/article/details/105241194）
>
> 扩容方式：1.7 数组扩容后存储位置计算不一样，1.8不需要hash，用oldlength&得出高低位
>
> 1.7有啥问题？ 线程安全，单链表性能问题

**1.8为什么改成尾插法**

> JDK1.8之前扩容的时候，头插法会导致链表反转，在多线程情况下会出现环形链表，导致取值的时候出现死循环，JDK1.8开始在同样的前提下就不会导致死循环，因为在扩容转移前后链表的顺序不变，保持之前节点的引用关系。
>
> 例： A线程和B线程同时向同一个下标位置插入节点，遇到容量不够开始扩容，重新hash，放置元素，采用头插法，后遍历到的B节点放入了头部，这样形成了环，如下图所示：
>
> <img src="https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20220922093154371.png" alt="image-20220922093154371" style="zoom: 33%;" />

**为什么在1.8中增加红黑树？**

> 当需要查找某个元素的时候，线性探索是最直白的方式，它会把所有数据遍历一遍直到找到你所查找的数据，对于数组和链表这种线性结构来说，当链表长度过长（数据有成百上千）的时候，会造成链表过深的问题，这种查找方式效率极低，时间复杂度是**O(n)**。简单来说红黑树的出现就是为了提高数据检索的速度。

**链表过深问题为什么不用二叉查找树代替，而选择红黑树？为什么不一直使用红黑树？**

> 二叉树在特殊情况下会变成一条线性结构，这就跟原来的链表结构一样了，选择红黑树就是为了解决二叉树的缺陷。
>
> 红黑树在插入数据的时候需要通过左旋、右旋、变色这些操作来保持平衡，为了保持这种平衡是需要付出代价的。当链表很短的时候，没必要使用红黑树，否则会导致效率更低，当链表很长的时候，使用红黑树，保持平衡的操作所消耗的资源要远小于遍历链表锁消耗的效率，所以才会设定一个阈值，去判断什么时候使用链表，什么时候使用红黑树

**讲一下你对红黑树的认识**

> 每个节点非红即黑
>
> 根节点总是黑色的
>
> 如果节点是红色，则它的子节点必须是黑色（反之不一定）
>
> 每个叶子节点都是黑色的空节点
>
> 从根节点到叶子节点或者空节点的每条路径必须包含相同数量的黑色节点（黑色节点的深度相同）

**HashMap线程安全吗？如何解决这个线程不安全的问题？**

> 不安全
>
> 可以使用HashTable、Collections.synchronizedMap、以及ConcurrentHashMap这些线程安全的Map。

**分别讲一下这几种Map都是如何实现线程安全的？**

> HashTable是直接在操作方法上加synchronized关键字，锁住整个数组，粒度比较大；
>
> Collections.synchronizedMap是使用Collections集合工具的内部类，通过传入Map封装出一个SynchronizedMap对象，内部定义了一个对象锁，方法内通过对象锁实现；
>
> ConcurrentHashMap在JDK1.7中使用分段锁，降低了锁粒度，让并发度大大提高，在JDK 1.8 中直接采用了CAS（无锁算法）+ synchronized的方式来实现线程安全。

更多hashmap相关面试题https://blog.csdn.net/cy973071263/article/details/126390401

**LinkedHashSet 底层LinkedHashMap**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image19.png" alt="img" style="zoom: 50%;" /> 



**Hashtable**

线程安全的hashmap

扩容  11 ，0.75

a<<1+1   11*2+1=23

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image20.png" alt="img" style="zoom:60%;" /> 



**ConCurrentHashMap**

**（专业HashMap相关讲解**[我敢说这是B站最详细的hashmap及ConcurrentHashMap底层原理解析，16个小时带你吃透所有知识_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1H64y1X7yM?p=3&spm_id_from=pageDriver) **）**

**1.7**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image21.png" alt="img" style="zoom:80%;" /> 

**Segment容量初始化后就不再变，扩容只针对每个Segment下的table数组进行扩容**

**1.8**

**数组并发处理还是CAS，put链表、树用syncronized,树化操作也是syncronized**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image22.png" alt="img" style="zoom:60%;" /> 



**addCount逻辑 (put完后数量要加1)**

**1计算size，分散线程竞争，有个线程CAS了basecount，其他线程则分散处理**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image23.png" alt="img" style="zoom:60%;" /> 

**代码如下：**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image24.png" alt="img" style="zoom:60%;" /> 

**2.扩容**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image25.png" alt="img" style="zoom:60%;" /> 





<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image26.png" alt="img" style="zoom:80%;" /> 

**SparseArray**

维护两个数组，key有序为int，通过二分查找（重点），查找要求低，要求节省内存可以用，但是数据量大，不适合

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image27.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image28.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image29.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image30.png" alt="img" style="zoom:80%;" /> 

**ArrayMap**

用于bundle，维护两个数组  hashCode  keyvalue



**堆的本质：二叉树的特性来维护数组**

**树和堆的区别：树使用指针，堆不使用指针，节约内存**

**SpareArray能提高性能，两个数组**

**堆优于树，堆用来排序，面试问排序，优先回答堆排序**

**二叉树最后一个非叶子节点就是length/2-1**

**算法导论4**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image31.png" alt="img" style="zoom:80%;" /> 



**TreeSet**

同TreeMap，key支持排序，重写compare方法，返回0则代表重复，添加key不成功，

如果是TreeMap，则key不能替换，但是value可以替换。

如 a,5   b,6  ->  a,6



**TreeMap**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image32.png" alt="img" style="zoom:80%;" /> 









#### JVM

##### JVM

![image-20220922205934597](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220922205934597.png)





**1.程序计数器 java内存区域中唯一不会OOM的  int**

**一.虚拟机栈 **

**栈有什么特点？**

>  入口出口只有一个，出栈 、入栈，先进后出

**为什么JVM要使用栈？**

> A方法中执行B方法中执行C方法，结束顺序是C-B-A，必须得先进后出。

**javac 和 javap作用**

> 执行`javac`编译得到.class文件，通过`javap -v`反编译得到字节码。



***线程私有***

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image34.png" alt="img" style="zoom:50%;" /> 



**1.操作数栈 局部变量表**

demo1：

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220808210548428.png" alt="image-20220808210548428" style="zoom: 80%;" />

![image-20220808210259058](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220808210259058.png)

左侧序号：程序计数器 记录当前线程执行到哪里（多线程切换后还能继续往下执行）

17调用方法：动态链接



demo2：

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image35.png" alt="img" style="zoom:60%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image36.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image37.png" alt="img" style="zoom:60%;" /> 

**2.动态连接**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image38.png" alt="img" style="zoom: 50%;" /> 

**3.完成出口（返回地址）**

A方法中执行B方法，B执行完后回到A中的地方





***线程共享***

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image39.png" alt="img" style="zoom:80%;" /> 



代码到图

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image40.png" alt="img" style="zoom:80%;" /> 



<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image41.png" alt="img" style="zoom:80%;" />





**直接放入老年代的场景**

> 大对象直接进入老年代
>
> 动态年龄判定：100M空间，1岁10M，2岁20M，3岁30M，加起来超过一半，则把3岁直接放入老年代
>
> 动态担保：多个对象都得存货，但是from和to中大小不够，只能放入老年代







<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image42.png" alt="img" style="zoom:80%;" /> 

栈溢出

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image43.png" alt="img" style="zoom:80%;" /> 

堆溢出

第一种

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image44.png" alt="img" style="zoom:80%;" /> 

第二种

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image45.png" alt="img" style="zoom:80%;" />  

**虚拟机优化技术**

**1.** **方法内联**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image46.png" alt="img" style="zoom:50%;" /> 

**2.** **栈帧共享数据**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image47.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image48.png" alt="img" style="zoom:80%;" /> 

**3.栈上分配**







##### 垃圾回收

判断对象是否存活

>  计数法
>
>  根可达



<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image49.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image50.png" alt="img" style="zoom: 50%;" /> 





**拓展：**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220808215440484.png" alt="image-20220808215440484" style="zoom: 60%;" />

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220808215538060.png"  style="zoom: 50%;" />

> 如果换成ArrayList，那么因为扩容的原因，会导致内存一直扩大，回收几次就直接OOM掉了，而LinkedList不需要扩容，频繁gc，但是因为Gcroots原因，回收不掉啥，所以会报gc限制异常



**面试题：可达性分析如果没有可达性，对象一定死吗？**

**答：如果重写了finalize方法，进行了引用，那么会救一次**



**四种引用**

> 强引用：内存不足都不会回收
>
> 软引用：内存不足再回收
>
> 弱引用：gc就回收
>
> 虚引用：随时可能被回收

虚引用作用基本上就是监听垃圾回收机制是否正常工作。

正常项目中用弱引用，软引用基本上都要OOM了才会回收，基本无效了，比如内存100M

强引用100M，软引用20M，回收也没啥用，但是如果用弱引用的化，再OOM之前就会频繁进行GC。



<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image52.png" alt="img" style="zoom:80%;" /> 

**空间分配担保**

> 首先，在执行任何一次Minor GC之前，JVM会先检查一下老年代可用的内存空间，是否大于新生代所有对象的总大小。为啥检查这个呢？因为最极端的情况下，可能新生代Minor GC过后，所有对象都存活下来了，那岂不是新生代所有对象全部要进入老年代？

**逃逸分析**

> 当变量（或者对象）在方法中分配后，其指针有可能被返回或者被全局引用，这样就会被其他方法或者线程所引用，这种现象称作指针（或者引用）的逃逸(Escape)。通俗点讲，如果一个对象的指针被多个方法或者线程引用时，那么我们就称这个对象的指针（或对象）的逃逸（Escape）。如下，第一段代码返回了stringBuilder，可能被其他地方改变，作用域就不在方法内了，发生了逃逸，改为第二段之后，就没有发生逃逸。
>
> ```tsx
> public StringBuilder escapeDemo1(String a, String b) {
>  StringBuilder stringBuilder = new StringBuilder();
>  stringBuilder.append(a);
>  stringBuilder.append(b);
>  return stringBuilder;
> }
> public String escapeDemo2(String a, String b) {
>  StringBuilder stringBuilder = new StringBuilder();
>  stringBuilder.append(a);
>  stringBuilder.append(b);
>  return stringBuilder.toString();
> }
> ```



**垃圾回收算法**

> 复制算法（新生代）
>
> 标记清除算法（老年代）
>
> 标记整理算法（老年代）

只有标记清除算法才会导致内存抖动后的OOM

内存泄漏：LeakCanary   KOOM   Matrix



**垃圾回收器**

> CMS收集器是基于“”标记--清除”(Mark-Sweep)算法实现的
>
> G1只有**并发标记**才不会stop-the-world，其他都会停下来。G1从整体来看是基于“标记整理”算法实现的收集器；从局部上来看是基于“复制”算法实现的。



##### JVM面试点

**0.常量池和String**

常量池存在于方法区，分为

**静态常量池**：class文件（类、方法信息、符号引用、字面量等）

**运行时常量池**：加载类后，直接引用，hash

**String**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image53.png" alt="img" style="zoom:80%;" /> 

**1.** **JVM内存结构简单说说**

> 线程私有、共享角度，虚拟机栈详细说说，其他几个一带而过

**2.** **什么情况下内存栈溢出**

> ①无限递归 stackoverflow
>
> ②不断创建线程，占用空间，机器不够，抛出oom

**3.** **new一个对象的流程**

> 类加载-检查-分配-内存空间初始化-设置-对象初始化（重点分配内存）

**4.** **对象是否会分配在栈中**

> 符号逃逸分析

**5.** **判断一个对象是否被回收**

> 引用计数法（缺点）、gcroots可达性分析（真实虚拟机使用）

**6.** **gc算法有哪些？特点是啥**

> 复制：简单高效，没有碎片，空间利用只有一半
>
> 标记清除：效率不稳定，对象招生西斯，适合老年代，内存碎片
>
> 标记整理：没有内存碎片，对象移动更新，用户线程暂停，效率低

**7.** **一次完整gc流程**

> 1. 当 Eden 区的空间满了， Java虚拟机会触发一次 Minor GC，以收集新生代的垃圾，存活下来的对象，则会转移到 Survivor区。
> 2. 大对象（需要大量连续内存空间的Java对象，如那种很长的字符串）直接进入老年态；
> 3. 如果对象在Eden出生，并经过第一次Minor GC后仍然存活，并且被Survivor容纳的话，年龄设为1，每熬过一次Minor GC，年龄+1，若年龄超过一定限制（15），则被晋升到老年态。即长期存活的对象进入老年态。
> 4. Major GC 发生在老年代的GC，清理老年区，经常会伴随至少一次Minor GC，比Minor GC慢10倍以上。
> 5. 老年代满了而无法容纳更多的对象，Minor GC 之后通常就会进行Full GC，Full GC 清理整个内存。

**8.** **String s=new String("aaa") 创建几个对象**

> 两个对象





---



#### 泛型

**泛型**：

> 把类型明确的工作推迟到创建对象或调用方法的时候才去明确的特殊的类型

**好处：**

> 1、避免了类型转换的麻烦，存储的是什么类型，取出的就是什么类型
> 2、把运行期异常提前到了编译期，

**泛型方法和泛型类、接口没有关系**，返回类型前加<T>，记住有这个才是泛型方法

```
static class Fruit{ }
static class Apple extends Fruit{ }
static class Person{ }
static class GenerateTest<T>{
    public void show_1(T t){}
    public <T> void show_2(T t){ }
}
public static void main(String[] args) {
    Apple apple = new Apple();
    Person person = new Person();
    GenerateTest<Fruit> generateTest = new GenerateTest<>();
    generateTest.show_1(apple);    //正常 子类泛型正常
    generateTest.show_1(person);   //报错 类型不一致
    generateTest.show_2(apple);    //正常 泛型方法和类泛型没有关系，即使一样都是T，也没有任何限制
    generateTest.show_2(person);   //正常 泛型方法和类泛型没有关系，即使一样都是T，也没有任何限制
}
```



**限定类型**

extends  派生

```
public static <T extends Comparable> T min(T a, T b){
    if(a.compareTo(b)>0) return a; else return b;
}
public class ClassBorder<T extends Comparable> {}
```

**局限性**

```
//静态域或者方法里不能引用类型变量（泛型类中的泛型参数的实例化是在定义对象的时候指定的）
//private static T instance;
//静态方法 本身是泛型方法就行
//private static <T> T getInstance(){}
//基础类型
//Restrict<double>
```



**通配符**（包含本身）

**提前热身：给定两种具体的类型A和B(例如Fruit和Apple),无论A和B是否相关，MyClass<A>与MyClass<B>都没半毛钱关系，它们的公共父对象是Object**

? extends （协变）

```
//extends   本身+子
public static void print2(GenericType<? extends Fruit> p){
    System.out.println(p.getData().getColor());
}
GenericType<Fruit> a = new GenericType<>();
GenericType<Orange> b = new GenericType<>();
print2(a);
print2(b);
```

用于安全的访问数据 不能set数据

```
GenericType<? extends Fruit> c =  new GenericType<>();
c.setData(new Apple());//报错
c.setData(new Fruit());//报错
Fruit x = c.getData();//限定后父类
```

所以有了**? super**（逆变）

```
//super   本身+父
public static void printSuper(GenericType<? super Apple> p){
      System.out.println(p.getData());
}
GenericType<Fruit> fruitGenericType = new GenericType<>();
GenericType<Apple> appleGenericType = new GenericType<>();
printSuper(fruitGenericType);
printSuper(appleGenericType);
```

```
//表示GenericType的类型参数的下界是Apple
GenericType<? super Apple> x = new GenericType<>();
x.setData(new Apple());   //set本身
x.setData(new HongFuShi()); //set子类
x.setData(new Fruit());   //报错
Object data = x.getData(); //万物父类Object
```

用于安全的写入数据，只能set本身或者子类，这里比较难理解，死记

和限定符区别：只能作用在方法上，不能作用在类上

以上其实就是PECS原则

总结一下，便于记忆：

> 协变：extends/向上转换/不能add/只能get（T及父类）
>
> 逆变：super/向下转换/不能get/只能add（T及子类）



通过以下案例理解协变

```
Integer integers = new Integer(3);
test(integers);
public static void test(Number list){
}
```

test方法的Number是Integer父类，所以可行，演变如下：

```
List<Integer> integers = new ArrayList<>();
test(integers);
public static void test(List<Number> list){  
}
```

对应类型添加为List，因为List是没有父类子类的说法，所以以上test方法会报错,此时利用泛型协变即可

```
List<Integer> integers = new ArrayList<>();
test(integers);
public static void test(List<? extends Number> list){
}
```



<?> 不能读，不能写



**泛型擦除**

虚拟机运行会T看成Object，如果是T extends ArrayList  ，则会擦除成ArrayList，如下IDE会报错，参数虽然不一样，但是虚拟机不认识。

```
同一个类中
public static void method(List<String> stringList){
    System.out.println("List");
}
public static void method(List<Integer> stringList){
    System.out.println("List");
}
```



**面试题：**

**为什么需要泛型**

> 优点：
>
> 1.使用泛型可以编写模板代码来适应任意类型，减少重复代码
>
> 2.使用时不必对类型进行强制转换，方便且减少出错机会
>
> 早期Java是使用Object来代表任意类型的，但是向下转型有强转的问题，这样程序就不太安全
>
> List list = new ArrayList();
> list.add("xxString");
> list.add(100d);
> list.add(new Person());
> 我们在使用上述list中，list中的元素都是Object类型（无法约束其中的类型），所以在取出集合元素时需要人为的强制类型转化到具体的目标类型，且很容易出现java.lang.ClassCastException异常。

**Java泛型的原理？什么是泛型擦除机制？**

> ]ava的泛型是JDK5新引入的特性，为了向下兼容，虚拟机其实是不支持泛型，所以Java实现的是一种
> **伪泛型**（编译器做处理，生成字节码还是原来那一套，不需要新增类型）机制，也就是说Jva在编译期擦除了所有的泛型信息，这样Java就不需要产生新的类型到字节码，
> 所有的泛型类型最终都是一种原始类型，在Jva运行时根本就不存在泛型信息。

**Java编译器具体是如何擦除泛型的**

> 1.检查泛型类型，获取目标类型
> 2.擦除类型变量，并替换为限定类型如果泛型类型的类型变量没有限定(<T>),则用Object作为原始类型
> 如果有限定(<T extends XClass>),则用XClass作为原始类型，如果有多个限定(T extends XClass.1&XClass2),则使用第一个边界XClass1作为原始类
> 3.在必要时插入类型转换以保持类型安全
> 4.生成桥方法以在扩展时保持多态性

**泛型类型变量不能使用基本数据类型**

> 比如没有ArrayList<int>,只有ArrayList<Integer>.当类型擦除后，ArrayList的原始类中的类型变量(T)替换成Object,但Object类型不能存放int值

**不能使用instanceof运算符**

> A:因为擦除后，ArrayList<String>只剩下原始类型，泛型信息String不存在了，所有没法使用instanceof

**泛型在静态方法和静态类中的问题**

> 因为泛型类中的泛型参数的实例化是在定义泛型类型对象(比如ArrayList<Integer>)的时候指定的，而静态成员是不需要使用对象来调用的，所有对象都没创建，如何确定这个泛型参数是什么

**泛型接口类的实现类必须要传具体实现吗？**

> 不需要。参考ArrayList

**为什么要使用泛型方法呢**？

> 因为泛型类要在实例化的时候就指明类型，如果想换一种类型，不得不重新new一次，可能不够灵活；而泛型方法可以在调用的时候指明类型，更加灵活。

**什么是泛型擦除？为什么要泛型擦除？如何证明泛型擦除了**（注意区分上面为什么要泛型）

> 泛型信息只存在于代码编译阶段，但是在java的运行期(已经生成字节码文件后)与泛型相关的信息会被擦除掉，专业术语叫做类型擦除。
>
> 泛型擦除的目的是为了向下兼容老的Java版本，老的Java版本是没有泛型概念的。
>
> 定义String和Integer两个ArrayList泛型类型，发现两者class相等；通过反射添加限制了String泛型的Integer类型成功

**泛型擦除会带来什么问题？**[参考](https://www.cnblogs.com/liuermeng/archive/2021/08/03/15096595.html)

> 1. **泛型类型变量不能是基本数据类型**，因为在进行类型擦除后，List 的原始类型会变为 Object，而 Object 类型不能存储 int 类型的值，只能存储引用类型 Integer 的值。
>
> 2. **编译时集合的instanceof**，
     >
     >    ```
>    List<String> list=new ArrayList<String>();
>    if( list instanceof ArrayList<String>) {}  //编译错误
>    if( list instanceof ArrayList<?>) {}  //正确的使用方法
>    ```
>
> 3. 泛型类中的静态方法和静态变量不可以使用泛型类所声明的泛型类型参数
     >
     >    ```
>    public class Test2<T> {
>        public static T one;   //编译错误    
>        public static  T show(T one){ //编译错误    
>            return null;
>        }
>    }
>    ```
     >
     >    因为泛型类中的泛型参数的实例化是在定义对象的时候指定的，而静态变量和静态方法不需要使用对象来调用。对象都没有创建，如何确定这个泛型参数是何种类型，所以当然是错误的。
     >
     >    还有其他几种比较难，参考上面链接

**如何在运行时获取泛型的类型**

> 没办法(非要拿可以通过反射，泛型信息呗擦除了，保留在了类常量池中)

**简述PECS原则**

> PECS原则全称"Producer Extends, Consumer Super"，使用 extends 确定上界的只能是生产者，只能往外生产东西，取出的就是上界类型。不能往里塞东西。使用 super 确定下界的只能做消费者，只能往里塞东西。取出的因为无法确定类型只能转成 Object 类型
>
> **协变**   <? extends People>限制了该引用的**实际泛型**一定是**People的某个子类**，即list1引用实际指向的ArrayList中只能存储People的子类（可以向上转型成People），但由于是“某个子类”，所以不**能确定其类型**，那么在进行存储时也就无法确定需要向上转型的泛型。
> 但是在读取时，因为实际的list中存储的一定是People的子类以及该子类的子类，那么一定可以用People引用进行读取（向上转型);对于协变 extends, 只 get；保证泛型类的类型安全;
>
> **逆变**  <? super Men>限制了该引用的**实际泛型**一定是**Men的某个父类**，即list2引用实际指向的ArrayList中只能存储Men的父类，那么在存储时，只需要使用Men类型的引用，那么一定可以向上转型成list的实际泛型。
> 但是在读取时，由于无法确定list的实际泛型，可能是Men的任意父类，只能用终极父类Object进行兜底读取。对于逆变 super, 只 set







---



#### 注解

![image-20230607154419283](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230607154419283.png)

![image-20230609161538053](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230609161538053.png)

使用场景：

> APT     :  butterknife源码（码牛1） arouter生成映射表  java-javac期间，process可以理解成javac回调
>
> 字节码：Aspectj 权限申请
>
> ​				javassist robust
>
> ​				asm gradle 插件中使用ASM
>
> 反射    ： 早期的buttnerknife，注解其实就是个标识



**final类型可以反射修改吗？**

> 正常可以修改，被final修饰的基本类型或者String，编译器会做内联优化，无法修改
>
> <img src="https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230607160350900.png" alt="image-20230607160350900" style="zoom:50%;" />



**反射为什么慢？**

> ①　invoke方法参数是object，比如原来方法是int，则装箱，然后再拆箱
>
> public native Object invoke(Object obj,object...args)   装箱
>
> ②　getdecaleredmethod要循环遍历，费时间
>
> for (auto&m h klass->GetDeclaredVirtualMethods (kPointerSize)){
>
> ③　检查方法可见性
>
> ④　编译器无法进行动态代码的优化，比如内联
>
> 但是反射影响性能微乎其微，



**动态代理：如retrofit**

在内存中生成一个代理类，实现所有方法，包含toString等，调用方法，其实就是调用了生成的动态代理类的对应方法，回调h.invoke(o, m,a),然后调用**反射**方法。

m.invoke(obj,a)

![image-20230607163914683](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230607163914683.png)

![image-20230607163727815](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230607163727815.png)

![image-20230607163809163](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230607163809163.png)



**动态代理是如何生成代理对象的呢？底层真的没有创建类吗？**

Proxy.newProxyInstance 会创建一个Class，与静态代理不同，这个Class不是由具体的.java源文件编译
而来，即没有真正的文件，只是在内存中按照Class格式生成了一个Class。

在Proxy中有一个静态内部类：ProxyClassFactory，在他的apply方法中可以看到这样一行代码

```java
String var23 = var16 + "$Proxy" + var19;
//第一参数表示类名，是拼装后的，第二个表示 Class<?>[] var1，代理接口数组
byte[] var22 = ProxyGenerator.generateProxyClass(var23, var2, var17);
```

通过这个方法会在内存中生成一个类的字节码数组，然后

```css
Proxy.defineClass0(var1, var23, var22, 0, var22.length);
```

定义出这个类，内存中就有这个类了。



最后总结一下：

> **动态代理通过传入的代理接口在内存中生成一个代理类的Class，这个代理类实现了我们传入接口的方法，如此一来，外部在拿到代理对象的时候就可以强转成对应的接口，调用相应的方法，调用方法后会执行代理对象中对应的方法，然后通过InvocationHandler的invoke方法将对应接口的对应方法的method和参数回调出来，然后再次使用反射，传入我们的实例对象，这样便达到了调用我们实例对象方法的目的**



------



#### 序列化

可参考https://juejin.cn/post/7196227432415576120（享2差不多）

序列化：将数据结构或对象转换成二进制串的过程。

反序列化：将在序列化过程中所生成的二进制串转换成数据结构或者对象的过程



序列化方案：json、xml、protbuf

##### **Serializable**

其实就是起到一个标识作用，告诉别人支持序列化，支持内存中，对于序列化，**底层的实现就是通过ObjectOutputStream**包装类将Courser转换为一组二进制字节数据

```
public fun serialize(user: User): ByteArray {
    val bos = ByteArrayOutputStream()
    val objectInputSystem = ObjectOutputStream(bos)
    //写数据
    objectInputSystem.writeObject(user)
    return bos.toByteArray()
}
public fun unSerialize(byteArray: ByteArray): User {
    val bis = ByteArrayInputStream(byteArray)
    val objectInputSystem = ObjectInputStream(bis)
    //读数据
    return objectInputSystem.readObject() as User
}
```

**ObjectOutputStream和ObjectInputStream可以认为是Java提供的一个序列化工具**，用于将数据拆分和组装。

**源码分析：**

> 1 序列化过程：writeObject方法中，主要就是干了两件事：
>
> （1）获取User类的全部类信息，包括方法、字段、SUID等等，将其封装在ObjectStreamClass中；
>
> （2）在拿到全部类信息后，会将全部的类信息以及字段数据转换成二进制数据。
>
> 2 反序列化：反射
>
> 就是通过反射的方式调用无参构造函数创建一个新的对象。因为**在序列化的时候，是将类的全部信息封装在了ObjectStreamClass中，所以反序列化的时候也会获取这些类信息，从而通过反射对所有的字段赋值**。

同时也支持本地磁盘FileOutputStream，如下

```
public static boolean saveObject(Object obj, String path) {//跟文件相关-属于持久化
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(path));// 创建序列化流对象
            oos.writeObject(obj);
            oos.close();
            return true;
        } catch (IOException e) {
        } finally {
        }
        return false;
    }
```





##### **Parcelable**

**为什么出现Parcelable**

> Android为了响应速度，舍弃了JVM，选择了基于寄存器的Dalvik和ART，所以Parcelable的出现目的---速度
>
> 首先Serializable的序列化和反序列化是基于IO的，需要做本地化的磁盘存储；还有一个问题就是，**在反序列化的过程中，需要通过反射的形式创建一些新的对象，这些对象也是被存放在堆内存中，会产生内存碎片，其本质还是一个深拷贝，如果发生频繁的反射调用对于性能上是有损耗的**。
>
> **Parcelable是基于Binder的，直接在内存中操作数据，减少了磁盘IO操作，但是因为Binder内存的限制，因此不能超过1M**，但是Serializable是没有限制的

Parcel提供了一套机制，可以将序列化之后的数据写入到一个共享内存中，其他进程通过

Parcel可以从这块共享内存中读出字节流，并反序列化成对象,下图是这个过程的模型。

不作为持久化方案！！

![image-20230612211140088](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20230612211140088.png)

**源码分析**

序列化

```
@Override
public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
}
往里面一直跟，会发现调用了原生本地方法，
private static native void nativeWriteString16(long nativePtr, String val);
```

反序列化

```
public Person createFromParcel(Parcel in) {
    return new Person(in);
}
往里面一直跟，会发现调用了原生本地方法，
private static native String nativeReadString16(long nativePtr);
```

会发现所有的操作都是通过Parcel类实现，但是序列化和反序列化的源码流程很简单，核心的数据处理都是采用的本地方法，其实是在本地开辟了一块共享内存，通过指针指向了这块内存，把数据存入了这里面。



**如何选择**

> Parcelable则是以IBinder作为信息载体，在内存上开销比较小，因此在内存之间进行数据传递时，推荐
>
> 使用Parcelable,而Parcelable对数据进行持久化或者网络传输时操作复杂，一般这个时候推荐使用
>
> Serializable

**如果一个类中的类成员变量不支持序列化，会发生什么情况**

> 进行序列化操作的时候报错。
>
> **序列化其实是一次深拷贝的操作**。对于浅拷贝（这里不考虑基本数据类型）只是将引用地址做一次拷贝；深拷贝则是需要重新创建一个对象，并把数据拷贝过去。

**如果某个类可以序列化，但是其父类不可以序列化，那么这个类可以序列化吗？**

> **反序列化的时候报错了，说明在序列化存储的时候是没问题的**，那么这里我们就需要看下readObject的源码了
>
>   obj = desc.isInstantiable() ? desc.newInstance() : null;
>
> 在**反序列化的时候类似于创建一个子类的过程，此时应该先创建父类，调用父类的构造方法，因为父类没有实现序列化接口，那么父类信息是缺失的，只能调用一个无参构造方法，那么此时父类没有空参构造方法，因此直接报错**。
>
> 反之，如果子类没实现序列化接口，而父类实现了，那么这种情况下是可以完成序列化的，因为继承关系，子类就能够获取父类的序列化能力。

**Parcelable和Serializable对比：**

Serializable通过IO对硬盘进行操作，速度较慢，大小不受限制，大量使用反射产生碎片；Parcelable直接在内存操作，效率高，大小一般不能超过1M；

**Android里面为什么要设计出Bundle而不是直接用HashMap结构**

> 1.Bundle采用ArrayMap，数据量少的情况下效率更高
>
> 2.在Android中如果使用Intent来携带数据的话，需要数据是基本类型或者是可序列化类型，HashMap使用Serializable进行序列化，而Bundle则是使用Parcelable进行序列化。而在Android平台中，更推荐使用Parcelable实现序列化，所以系统封装了Bundle；
>
> ```
> class Bundle extends BaseBundle implements Cloneable, Parcelable{}
> class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable {}
> ```
>
> 3、为何Intent不能直接在组件间传递对象而要通过序列化机制？

> Intent在启动其他组件时，会离开当前应用程序进程，进入ActivityManagerService进程（intent.prepareToLeaveProcess()），这也就意味着，Intent所携带的数据要能够在不同进程间传输。首先我们知道，Android是基于Linux系统，不同进程之间的java对象是无法传输，所以我们此处要对对象进行序列化，从而实现对象在 应用程序进程 和 ActivityManagerService进程 之间传输。
> 而Parcel或者Serializable都可以将对象序列化，其中，Serializable使用方便，但性能不如Parcel容器，后者也是Android系统专门推出的用于进程间通信等的接口。
>
> 附加知识：
> 在不同进程之间，常规数据类型可以直接传递，如整数，以传递字符串为例，要从A进程传递到B进程，只需在B进程的内存区开辟一样大小的空间，然后复制过去即可。
> 但是，对象却不能直接跨进程传递。即使成员变量值能传递过去，成员方法是无法传递过去的，此时如果B进程要调用成员方法则出错。
> 具体传递对象的方法：
> \1. 在进程Ａ中把类中的非默认值的属性和类的唯一标志打成包（这就叫序列化）；
> \2. 把这个包传递到进程Ｂ；
> \3. 进程Ｂ接收到包后，根据类的唯一标志把类创建出来（java反射机制）；
> \4. 然后把传来的属性更新到类对象中。
> 这样进程Ａ和进程Ｂ中就包含了两个完全一样的类对象。
>
> 附加知识：
> 在不同进程之间，常规数据类型可以直接传递，如整数，以传递字符串为例，要从A进程传递到B进程，只需在B进程的内存区开辟一样大小的空间，然后复制过去即可。
> 但是，对象却不能直接跨进程传递。即使成员变量值能传递过去，成员方法是无法传递过去的，此时如果B进程要调用成员方法则出错。
> 具体传递对象的方法：



#### IO流

**字节流（参考序列化）**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image59.png" alt="img" style="zoom:80%;" /> 



**字符流 (中文、英文)**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image60.png" alt="img" style="zoom:80%;" /> 



区别：中文 一个字符=两个字节

英文 一个字符=一个字节

字符流有编码 字节流灭有

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image61.png" alt="img" style="zoom:80%;" /> 



补充：**ObjectOutputStream** 序列化到本地

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image62.png" alt="img" style="zoom:80%;" /> 



**RandomAccessFile  seek**指哪打哪  用处：断点续传

---



#### 单例

饿汉式

> 优点：线程绝对安全，无锁，效率高。缺点：类加载的时候就初始化，不管用不用，都占用空间。

```
//饿汉式单例类.在类初始化时，已经自行实例化 
public class Singleton1 {
    private Singleton1() {}
    private static final Singleton1 single = new Singleton1();
    //静态工厂方法 
    public static Singleton1 getInstance() {
        return single;
    }
}
```

懒汉式

> 优点：在外部需要使用的时候才进行实例化 缺点：线程不安全

```
//懒汉式单例类.在第一次调用的时候实例化自己 
public class Singleton {
    private Singleton() {}
    private static Singleton single=null;
    //静态工厂方法 
    public static Singleton getInstance() {
         if (single == null) {  
             single = new Singleton();
         }  
        return single;
    }
}
```

> 在 Java 中，类的静态方法也是在类加载的过程中就已经被初始化了。但是和静态变量不同，静态方法的执行不是在类加载的过程中自动触发的，而是需要显式调用该静态方法才会执行。
>
> 在饿汉式中，单例对象是通过静态变量来实现的，因此在类加载时就已经初始化了该静态变量，从而创建了单例对象。但是在该单例对象中定义的静态方法并不会在类加载时自动执行，而是需要显式调用该方法才会执行。
>
> 总之，在类加载的过程中，静态变量和静态方法都会被初始化，但是静态方法的执行需要显式调用，而静态变量的初始化则是自动完成的。

双重检查懒汉式单例

```dart
public class Singleton {  
  	//volatile关键字的作用其实就是让该变量的变化对于每一个线程可见(详见juc部分)
    private volatile static Singleton singleton; 
    private Singleton (){}   
    public static Singleton getInstance() {  
      if (singleton == null) {  //第一次检测：由于单例模式只需要创建一次实例，如果后面再次调用getInstance方法时，则直接返回之前创建的实例，因此大部分时间不需要执行同步方法里面的代码，大大提高了性能。如果不加第一次校验的话，每次都要去竞争锁
          synchronized (Singleton.class) {  
          if (singleton == null) {//第二次检测：如果没有第二次校验，假设线程t1执行了第一次校验后，判断为null，这时t2也获取了CPU执行权，也执行了第一次校验，判断也为null。接下来t2获得锁，创建实例。这时t1又获得CPU执行权，由于之前已经进行了第一次校验，结果为null（不会再次判断），获得锁后，直接创建实例。结果就会导致创建多个实例。所以需要在同步代码里面进行第二次校验，如果实例为空，则进行创建。
              singleton = new Singleton();  
          	}  
          }  
      }  
      return singleton;  
    }  
}
```

> 优点：线程安全 缺点：synchronized性能问题

静态内部类式 安全

```php
public class Singleton {  
    private Singleton (){}  
    public static final Singleton getInstance() {  
      // 返回结果以前，一定会先调用加载内部类
      return SingletonHolder.INSTANCE;  
    }   
    /**
     1 类装载的机制来保证初始化实例式只有一个线程。
   * 2 静态内部类方式在Singleton类被装载时并不会立即实例化，而是在需要实例化时，调用getInstance方法，才会装载SingletonInstance类，从而完成Singleton的实例化。
     3 类的静态属性只会在第一次加载类的时候初始化，所以在这里，JVM帮助我们保证了线程的安全性，在类进行初始化时，别的线程时无法进入的。
   */
    private static class SingletonHolder {  
      private static final Singleton INSTANCE = new Singleton();  
    }  
}
使用：
  Singleton.getInstance();
```

>  优点：完美地屏蔽了饿汉式的内存浪费，和synchronized性能问题 ，史上最牛B的单例模式的实现方式

枚举 安全

```java
public enum Singleton { 
    INSTANCE;  
  public static Singleton getInstance() {  
      return INSTANCE;  
    }
}
```

> 优点：可以天然的防止反射和反序列 化漏洞

---



#### 虚拟机

jvm 基于栈的虚拟机    执行class文件
dalvik 基于寄存器的虚拟机  执行dex文件









#### ClassLoader

**BootClassLoader和PathClassLoader关系**

framework ------BootClassLoader（系统）

> 如String.class.getClassLoader 指的是String是被谁給加载的

其他---------------PathClassLoader（应用）

> AppCompatActivity，第三方库

创建PathClassLoader 会将BootClassLoader传递进去作为parent，非类继承上的父亲，类似链表成员变量



**ClassLoader 加载类原理：**

> ClassLoader.loadClass -> DexPathList.loadClass -> 遍历dexElements数组 ->DexFile.loadClassBinaryName
>
> 通俗点说就是：ClassLoader加载类的时候是通过遍历dex数组，从dex文件里面去加载一个类，加载成功就返回，加载失败则抛出Class Not Found 异常。

**为什么要用双亲委托模式？**

> 可以避免重复加载：当父加载器已经加载了该类的时候，就没有必要子ClassLoader再加载一次。
> 安全性考虑：防止核心API库被随意篡改。我们试想一下，如果不使用这种委托模式，那我们就可以随时使用自定义的String来动态替代java核心api中的定义类型，这样会存在非常大的安全隐患。

```
//如果在PathClasLoader中，那么parent系统传递进来的就是BootClassLoader，而非类的父亲
protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
{
        Class<?> c = findLoadedClass(name);//缓存
        if (c == null) {
            try {
                if (parent != null) {
                    c = parent.loadClass(name, false);//父类加载器
                } else {
                    c = findBootstrapClassOrNull(name);
                }
            } catch (ClassNotFoundException e) {
            }
            if (c == null) {
                c = findClass(name);//找PathClassLoader中，没有就找它的类父亲BaseDexClassLoader
            }
        }
        return c;
}
```

```
BaseDexClassLoader.class
@Override
130    protected Class<?> findClass(String name) throws ClassNotFoundException {
131        List<Throwable> suppressedExceptions = new ArrayList<Throwable>();
132        Class c = pathList.findClass(name, suppressedExceptions);
133        if (c == null) {
136            for (Throwable t : suppressedExceptions) {
137                cnfe.addSuppressed(t);
138            }
139            throw cnfe;
140        }
141        return c;
142    }
 
 DexPathList.class
 public Class<?> findClass(String name, List<Throwable> suppressed) {
485        for (Element element : dexElements) {//遍历dex寻找
486            Class<?> clazz = element.findClass(name, definingContext, suppressed);
487            if (clazz != null) {
488                return clazz;
489            }
490        }
491
492        if (dexElementsSuppressedExceptions != null) {
493            suppressed.addAll(Arrays.asList(dexElementsSuppressedExceptions));
494        }
495        return null;
496    
```

以上时序图如下：

![image-20230714095925401](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230714095925401.png)



<img src="https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230714102905249.png" alt="image-20230714102905249" style="zoom:50%;" />



**PathClassLoader**和**DexClassLoader**在8.0后没区别，在8.0(AP126)之前，它们二者的唯一区别是
第二个参数optimizedDirectory,这个参数的意思是生成的odex(优化的dex)存放的路径。在8.0(API26)及之后，二者就完全一样了。

```
public class PathClassLoader extends BaseDexClassLoader {
    public PathClassLoader(String dexPath, ClassLoader parent) {
        super(dexPath, null, null, parent);
    }
    public PathClassLoader(String dexPath, String librarySearchPath, ClassLoader parent) {
        super(dexPath, null, librarySearchPath, parent);
    }
}

public class DexClassLoader extends BaseDexClassLoader {
    public DexClassLoader(String dexPath, String optimizedDirectory,
            String librarySearchPath, ClassLoader parent) {
        super(dexPath, null, librarySearchPath, parent);
    }
}
```

DexClassLoader小练习：加载某个class文件并打印方法

```
public class Test {
    public static void print(){
			Log.e(tag:"leo",msg:"print:启动插件方法");
	}
}
```

1.dx工具打包class为dex文件（该dex中只有一个class）

```
dx --dex--output=output.dex input.class
```

2.加载dex文件，并执行方法

```
 DexClassLoader dexclassLoader new DexclassLoader(dexPath:"/sdcard/test.dex",
                MainActivity.this.getCacheDir().getAbsolutePath(),
                librarySearchPath:null,
                MainActivity.this.getclassLoader());
       try {
            Class<?> clazz = dexclassLoader.loadclass(name:"com.enjoy.plugin.Test");
            Method print = clazz.getMethod(name:"print");
            print.invoke(null);
       } catch (Exception e) {
            e.printstackTrace();
       }
```



延伸插件化、热修复





---



#### 加固

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image65.png" alt="img"  /><img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image66.png" alt="img" style="zoom: 67%;" />

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image67.png" alt="img" style="zoom:80%;" /> 



脱壳：

解密aar的application中获取安装apk，解压后进行_.dex解密，然后吧所有dex文件加载进内存，v19.install(dexs)

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image68.png" alt="img" style="zoom:80%;" /> 



**对App dex进行加固的基本步骤如下：**

> 1. 从App原始apk文件里获取到原始dex文件
>
> 2. 对原始dex文件进行加密，并将加密后的dex文件和相关的存放到assert目录里
> 3. 用脱壳dex文件替换原始apk文件里的dex文件；脱壳dex文件的作用主要有两个，一个是解密加密后的dex文件；二是基于dexclassloader动态加载解密后的dex文件
> 4. 因为原始apk文件已经被修改，所以需要删除原始apk的签名信息，即删除META-INF目录下的.RSA、.SF 和MANIFEST.MF文件 \
> 5. 生成加固后的apk文件
> 6. 对加固后的apk文件进行签名，apk加固完成。

**原理分析：**

> 1.为什么要对原始dex进行加密，同时用脱壳dex文件替换原始dex文件？大部分的apk反编译工具（dex2jar、apktools、jui等）都是对dex文件进行反编译，将dex文件反编译成smail，然后再转化成class文件进行阅读和修改。用脱壳dex替换原始dex文件之后，用上面的反编译工具反编译apk文件，只能看到脱壳程序的class文件，看不到apk本身的class文件。对dex文件进行加密，这样即使第三方拿到了dex文件，以为无法解密，也就无法对其进行解析和分析。
>
> 2.怎么确保apk功能正常运行？加固后的apk启动之后，脱壳dex文件会对加密后的dex文件进行解密，然后机遇dexclassload动态加载解密后的dex文件。从用户的角度，加固前后App的功能和体验基本是一样的。这个和插件化的原理是一样的。



疑问：

1 三方加固并没有集成到app，是如何做到解密的？

猜测：   1 hook代码

​      2 解密class打包dex

2 application是否不能进行加密

以上两个问题 见下图

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image69.png" alt="img" style="zoom:80%;" /> 

真实的三方加固解密代码并没有在主工程进行以来，也就是上面的fix_library,直接由加固时进行侵入，详见[Android应用加固原理 - 掘金 (juejin.cn)](https://juejin.cn/post/6844903952345989134)







#### JUC

##### **线程**

**并行和并发**

> 并发针对单核 CPU 而言，它指的是 CPU 交替执行不同任务的能力；并行针对多核 CPU 而言，它指的是多个核心同时执行多个任务的能力。
>
> 单核 CPU 只能并发，无法并行；换句话说，并行只可能发生在多核 CPU 中。
>
> 在多核 CPU 中，并发和并行一般都会同时存在，它们都是提高 CPU 处理任务能力的重要手段。

**时间片轮转**

> 时间片轮转调度是一种最古老、最简单、最公平且使用最广的算法,又称RR调度。每个进程被分配一个时间段,称作它的时间片,即该进程允许运行的时间。

**Java**线程调度算法有哪些

> 抢占式调度、时间片轮转调度、其他调度

**什么是进程和线程**

> 进程：是程序运行资源分配的最小单位。程序的实例。
>
> 线程：是CPU调度的最小单位,必须依赖于进程而存在，它可与同属一个进程的其他的线程共享进程所拥有的全部资源。
>
> 线程是进程的一部分，一个线程只能属于一个进程，而一个进程可以有多个线程，但至少有一个线程。





**中断线程**

**问：stop()**方法为什么不建议使用？那用啥

> 比较野蛮，线程所占用的资源不能正常的释放
>
> **interrupt()** 对线程进行中断标记，不会强制中断，run中还需要自己处理标记位

![image-20230712163044453](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230712163044453.png)

如果UseThread是个runnable，那么使用Thread.currentThread().isInterrupted()



**问：isInterrupted() 和 static方法interrutpted区别？**

> 前者只是拿标记位，后者不仅拿而且还会改为false
>



下面代码可以在中断前进行资源的释放

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image70.png" alt="img" style="zoom:80%;" /> 



**wait/**[**notify**](https://so.csdn.net/so/search?q=notify&spm=1001.2101.3001.7020)

**问：如何保证两个线程顺序执行？**

> join()  让线程执行进行串行 ,join所在线程必须等待对应线程执行完
>
> T3{T2.join(T1.join())}
>

**join()方法是用`wait()`方法实现，但为什么没有通过`notify()`系列方法唤醒呀，如果不唤醒，那不就一直等待下去了吗？**

> 原因是：在java中，Thread类线程执行完run()方法后，一定会自动执行notifyAll()方法

**问：守护线程是啥？**

> 服务于用户线程，如下
>
> 主线程结束，子线程也会结束，不设置守护，主线程会一直等待子线程结束才会结束。

![image-20230712164458869](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230712164458869.png)

**生命周期**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image79.png" alt="img" style="zoom:100%;" />





wait  notify  yield sleep

wait 释放锁 yield 和 sleep 不释放

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image71.png" alt="img" style="zoom:80%;" /> 

**问：采用多线程技术，例如wait/**[**notify**](https://so.csdn.net/so/search?q=notify&spm=1001.2101.3001.7020)**，设计实现一个符合生产者和消费者问题的程序，对某一个对象（枪膛）进行操作，其最大容量是20颗子弹，生产者线程是一个压入线程，它不断向枪膛中压入子弹，消费者线程是一个射出线程，它不断从枪膛中射出子弹**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image72.png" alt="img" style="zoom:80%;" /><img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image73.png" alt="img" style="zoom:80%;" />











同步和异步两种方式

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image74.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image75.png" alt="img" style="zoom:80%;" /> 

**开启线程两种方式**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image76.png" alt="img" style="zoom:100%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image77.png" alt="img" style="zoom:100%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image78.png" alt="img" style="zoom:100%;" /> 





##### **JMM内存模型**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image80.png" alt="img" style="zoom:60%;" /> 

##### **Volatile**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image81.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image82.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image83.png" alt="img" style="zoom:80%;" /> 

1保证可见性 	 不能保证原子性 适用于一写多读（只有一个线程写，多个线程读）

2防止指令重排序  双重检锁单例   添加lock指令，不进行重排

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image84.png" alt="img" style="zoom:80%;" /> 

##### **Synchronized**（悲观锁）

this  对象锁    class 类锁（单例）

原理：

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image85.png" alt="img" style="zoom:80%;" /> 



<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image86.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image87.png" alt="img" style="zoom:80%;" /> 

**重量级Monitor**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image88.png" alt="img" style="zoom:80%;" /> 

**优化**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image89.png" alt="img" style="zoom:60%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image90.png" alt="img" style="zoom:60%;" /> 





##### **CAS（乐观锁）**

Sys原子操作（要么做完，要么不做）（悲观锁）

是一种策略，为了保证主内存中数据被多个线程赋值是一个准确的

方案:存旧值，主存对比，不一样则重新来

两个线程同时写，只用volatile只能保证可见性，不能保证结果的准确性，故配合CAS方案



**CAS原理**：

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image91.png" alt="img" style="zoom:50%;" /> 



Sync 很容易阻塞，就会导致上下文切换，唤醒比较耗时，性能低

CAS  线程不会阻塞，只会重试（无锁化）

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image92.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image93.png" alt="img" style="zoom:50%;" /> 

多的话可以用线程池转个位数

**CAS的问题**

> 1. ABA问题
>
> 线程1  A---->B   if A -------->B
>
> 线程2  A----->C----->A
>
> > 因为CAS需要在操作值的时候检查下值有没有发生变化，如果没有发生变化则更新，但是如果一个值原来是A，变成了B，又变成了A，那么使用CAS进行检查时会发现它的值没有发生变化，但是实际上却变化了。ABA问题的解决思路就是使用版本号。在变量前面追加上版本号，每次变量更新的时候把版本号加一，那么A-B-A 就会变成1A-2B-3A
>
> 2. 开销问题 线程不休息，一直在循环（自旋）
>
> 3. 只能保证一个共享变量的原子操作-使用范围
>
> { A  甲  1 } 修改不适应，此时就用sysnchnize



解决CAS问题

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image94.png" alt="img" style="zoom:60%;" /> 

##### **AQS-ReentrankLock**

核心state

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image95.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image96.png" alt="img" style="zoom:60%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image97.png" alt="img" style="zoom:80%;" /> 



##### **死锁**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image98.png" alt="img" style="zoom:80%;" /> 

![image-20230731105703470](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230731105703470.png)

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image100.png" alt="img" style="zoom:100%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image101.png" alt="img" style="zoom:100%;" /><img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image102.png" alt="img" style="zoom:100%;" />



##### **ThreadLocal**

用HashMap模拟实现ThreadLocal功能

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image103.png" alt="img" style="zoom:100%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image104.png" alt="img" style="zoom:100%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image105.png" alt="img" style="zoom:80%;" />

**ThreadLocal为何不用HashMap或者ConcurrentHashMap**

> 答案：因为无论使用HashMap或者ConcurrentHashMap，在底层上都会造成多个线程在一个对象上进行竞争的情况，并没有真正意义上满足一个线程持有一个变量的本地副本这种设计思想，ThreadLocal在实现上，是让每个线程在自己的内部单独持有一个变量，这样的话，线程之间就不会有竞争出现了。（上面例子可以简单理解成多个线程对hashmap数据的竞争，因为线程在外面，会产生竞争，他们不认识hashmap里面的东西是啥，类似抢篮球的钥匙）

**ThreadLocal是如何定位数据的**

> 参考hashmap数组，hash定位index，**线性探测法/开放地址法**来解决hash冲突，HashMap则通过**链地址法**来解决hash冲突
>
> ThreadLocal从数组中找数据的过程大致是这样的：
>
> 1. 通过key的hashCode取余计算出一个下标。
> 2. 通过下标，在数组中定位具体Entry，如果key正好是我们所需要的key，说明找到了，则直接返回数据。
> 3. 如果第2步没有找到我们想要的数据，则从数组的下标位置，继续往后面找。
> 4. 如果第3步中找key的正好是我们所需要的key，说明找到了，则直接返回数据。
> 5. 如果还是没有找到数据，再继续往后面找。如果找到最后一个位置，还是没有找到数据，则再从头，即下标为0的位置，继续从前往后找数据。
> 6. 直到找到第一个Entry为空为止。

**为什么用ThreadLocal做key？**

> 假如使用`Thread`做key时，你的代码中定义了3个ThreadLocal对象，那么，通过Thread对象，它怎么知道要获取哪个ThreadLocal对象呢

**Entry的key为什么设计成弱引用**

> 即使ThreadLocal变量生命周期完了，设置成null了，但由于key对ThreadLocal还是强引用。
> 此时，如果执行该代码的`线程`使用了`线程池`，一直长期存在，不会被销毁。存在这样的`强引用链`：Thread变量 -> Thread对象 -> ThreadLocalMap -> Entry -> key -> ThreadLocal对象。
> 那么，ThreadLocal对象和ThreadLocalMap都将不会被`GC`回收，于是产生了`内存泄露`问题。

小拓展：

```
Object object = new Object();
WeakReference<Object> weakReference1 = new WeakReference<>(object);
System.out.println(weakReference1.get());
System.gc();
System.out.println(weakReference1.get());
```

如上代码，不会被回收，因为创建时候强引用，后面再弱引用，已经回收不掉了，如果想回收，在gc前先null，或者

`WeakReference<Object> weakReference1 = new WeakReference<>(new Object());`

**Entry的value为什么不设计成弱引用**

> Entry的value如果只是被Entry引用，有可能咩被业务系统中的其他地方引用。如果将value改成了弱引用，被GC贸然回收了（数据突然没了），可能会导致业务系统出现异常

**ThreadLocal使用到了弱引用，是否意味着不会存在内存泄露呢？**

> 首先来说，如果把ThreadLocal置为null，那么意味着Heap中的ThreadLocal实例不在有强引用指向，只有弱引用存在，因此GC是可以回收这部分空间的，也就是key是可以回收的。但是value却存在一条从Current Thread过来的强引用链。因此只有当Current Thread销毁时，value才能得到释放。
>
> 因此，只要这个线程对象被gc回收，就不会出现内存泄露，但在threadLocal设为null和线程结束这段时间内不会被回收的，就发生了我们认为的内存泄露。最要命的是线程对象不被回收的情况，比如使用线程池的时候，线程结束是不会销毁的，再次使用的，就可能出现内存泄露。

**那么如何有效的避免呢**

> 事实上，在ThreadLocalMap中的set/getEntry方法中，会对key为null（也即是ThreadLocal为null）进行判断，如果为null的话，那么是会对value置为null的。我们也可以通过调用ThreadLocal的remove方法进行释放！







---



##### **线程池**

###### **参数说明、工作流程**

\1. 降低资源消耗

\2. 提高响应速度

\3. 提高线程可管理性

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image106.png" alt="img" style="zoom:80%;" /> 

![img](https://upload-images.jianshu.io/upload_images/13797352-c5cc01a97411af8a.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image107.png" alt="img" style="zoom:80%;" /> 

场景一：

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image108.png" alt="img" style="zoom:50%;" /> 

处理完一个请求后，移除running中自己，然后取ready中数据到running继续执行

场景二：

> 实验场景：核心线程数 = 1 ，最大线程数 = 3，开启 for 循环执行 10 个任务，任务内容：sleep 1 秒并打印当前线程名
>
> 当阻塞队列容量无限大时，10 个任务只出现 1 个线程在排队执行
>
> 当阻塞队列容量设置为 10 个或者 9 个时，10 个任务也是只出现 1 个线程在排队执行
>
> 当阻塞队列容量设置为 8 个时，10 个任务出现了 2 个线程在并发执行
>
> 当阻塞队列容量设置为 7 个时，10 个任务出现了 3 个线程在并发执行
>
> 当阻塞队列容量设置为 6 个时，线程池抛出异常，表示拒绝执行任务



io操作基本不用cpu

补充：

###### **阻塞队列**

BlockingQueue

\2. take  空队列取数据  阻塞

\3. put   队列已满，塞数据  阻塞

用于平衡生产者和消费者

rxjava 背压

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image109.png" alt="img" style="zoom:80%;" /> 

ArrayBlockingQueue ----- Asynclayoutinflater

**拒绝策略**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image112.png" alt="img" style="zoom:70%;" /> 

**几种创建方式**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image113.png" alt="img" style="zoom:70%;" /> 

###### **课外：fork/join线程池**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image114.png" alt="img" style="zoom:70%;" /> 



用于数据量大 耗时的操作，可以大幅度减少时间，类似多线程递归

demo1 分线程计算



demo2 找文件夹下最大的文件







##### FutureTask

通过继承Thread类或者实现Runnable接口这两种方式来创建多线程，但是这两种方式都有个缺陷，就是不能在执行完成后获取执行的结果，**AsyncTask**就是利用这个来实现结果的返回。

```
 FutureTask<String> futureTask = new FutureTask<String>(new MyCallable()){
            @Override
            protected void done() {
                try {
                    /*  获取 MyCallable 的 call 方法耗时操作的结果
                        注意 FutureTask 对象的 get() 最好在 done 中调用 , 可以立刻得到异步操作的执行结果
                        如果调用 get() 方法时 , Callable 的 call() 方法还没有执行完毕 ,
                        此时调用线程就会一直阻塞 , 直到 call() 方法是调用完毕 ,
                        返回执行结果 , 此时才会解除阻塞 , 返回执行结果 ;*/
                    String callableResult = get();  //会阻塞直到任务执行结束返回结果为止
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
```





##### **面试**

**1.** **什么是可见性？怎么解决？**

一个线程修改变量后，其他线程能够立即看到修改的值。volatile或者加锁

**2.** **锁分哪几类？**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image115.png" alt="img" style="zoom:80%;" /> 



**3.** **无锁化编程的原理**

CAS 地址上的变量，比较变量值和期望是否一样，交换为新值，不一样则重试

**4.** **CAS有哪些问题？**

①　ABA问题

线程1  A---->B   if A -------->B

线程2  A----->C----->A

②　开销问题 线程不休息，一直在循环（自旋）

③　只能保证一个共享变量的原子操作

{ A  甲  1 } 修改不适应，此时就用sysnchnize

**5.** **ReentrantLock实现原理**

可重入锁 线程没进入锁一次，计数器累加，释放累减，0则释放锁，利用juc并发包的	aqs来实现

**6.** **AQS原理（todo）**

是用来构建锁或者其他同步组件的基础框架，比如ReentrantLock、ReentrantReadWriteLock和CountDownLatch就是基于AQS实现的。它使用了一个int成员变量表示同步状态，通过内置的FIFO队列来完成资源获取线程的排队工作。它是CLH队列锁的一种变体实现。它可以实现2种同步方式：独占式，共享式。

AQS的主要使用方式是继承，子类通过继承AQS并实现它的抽象方法来管理同步状态，同步器的设计基于模板方法模式，所以如果要实现我们自己的同步工具类就需要覆盖其中几个可重写的方法，如tryAcquire、tryReleaseShared等等。

这样设计的目的是同步组件（比如锁）是面向使用者的，它定义了使用者与同步组件交互的接口（比如可以允许两个线程并行访问），隐藏了实现细节；同步器面向的是锁的实现者，它简化了锁的实现方式，屏蔽了同步状态管理、线程的排队、等待与唤醒等底层操作。这样就很好地隔离了使用者和实现者所需关注的领域。

在内部，AQS维护一个共享资源state，通过内置的FIFO来完成获取资源线程的排队工作。该队列由一个一个的Node结点组成，每个Node结点维护一个prev引用和next引用，分别指向自己的前驱和后继结点，构成一个双端双向链表。

**7.** **Synchronized原理以及和ReentrantLock区别**

Synchronized原理字节码中会多出两个字符monitorenter和monitorexit，前者尝试获取monitor相关联的	对象，执行代码，后者释放monitor对象的锁，相对于普通方法，其常量池中多了ACC_SYNCHRONIZED标示符

区别：存在层面：Syncronized 是Java 中的一个关键字，存在于 JVM 层面，Lock 是 Java 中的一个接口

锁的释放条件：1. 获取锁的线程执行完同步代码后，自动释放；2. 线程发生异常时，JVM会让线程释放锁；Lock 必须在 finally 关键字中释放锁，不然容易造成线程死锁

锁的获取: 在 Syncronized 中，假设线程 A 获得锁，B 线程等待。如果 A 发生阻塞，那么 B 会一直等待。在 Lock 中，会分情况而定，Lock 中有尝试获取锁的方法，如果尝试获取到锁，则不用一直等待

锁的状态：Synchronized 无法判断锁的状态，Lock 则可以判断

锁的类型：Synchronized 是可重入，不可中断，非公平锁；Lock 锁则是 可重入，可判断，可公平锁

锁的性能：Synchronized 适用于少量同步的情况下，性能开销比较大。Lock 锁适用于大量同步阶段：

Lock 锁可以提高多个线程进行读的效率(使用 readWriteLock)

在竞争不是很激烈的情况下，Synchronized的性能要优于ReetrantLock，但是在资源竞争很激烈的情况下，Synchronized的性能会下降几十倍，但是ReetrantLock的性能能维持常态；

ReetrantLock 提供了多样化的同步，比如有时间限制的同步，可以被Interrupt的同步（synchronized的同步是不能Interrupt的）等。



**8.** **Synchronized做了哪些优化？**

☆为了提升性能jdk引入了自旋锁，适应式自旋锁，偏向锁，轻量锁

还引入了

锁消除：不可能发生共享竞争，就不加锁了

锁粗化：增加锁的粒度，吧没有锁的也包进来，减少上下文切换

逃逸分析：枷锁的对象不会逃逸出方法外，只在方法内，虚拟机会优化

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image116.png" alt="img" style="zoom:80%;" /> 

**9.** **Synchronized静态和非静态锁的区别和范围**

静态方法类似等价于synchronized(AA.class)





**10.** **volatile能否保证线程安全？在DCL上作用是啥？**

不能！防止指令重排序

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image117.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image118.png" alt="img" style="zoom:100%;" /> 



<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image119.png" alt="img" style="zoom:100%;" /> 

为什么饿汉式 懒汉式可以保证线程安全？

因为虚拟机任意时刻只有一个线程能够进行类加载机制

**11.** **volatile和synchronize有什么区别？**

volatile是最轻量的同步机制。

volatile保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。但是volatile不能保证操作的原子性，因此多线程下的写复合操作会导致线程安全问题。

关键字synchronized可以修饰方法或者以同步块的形式来进行使用，它主要确保多个线程在同一个时刻，只能有一个线程处于方法或者同步块中，它保证了线程对变量访问的可见性和排他性，又称为内置锁机制。

**12.** **什么是守护线程？如何退出一个线程？**

Daemon（守护）线程是一种支持型线程，因为它主要被用作程序中后台调度以及支持性工作。这意味着，当一个Java虚拟机中不存在非Daemon线程的时候，Java虚拟机将会退出。可以通过调用Thread.setDaemon(true)将线程设置为Daemon线程。我们一般用不上，比如垃圾回收线程就是Daemon线程。

线程的中止：

要么是run执行完成了，要么是抛出了一个未处理的异常导致线程提前结束。

暂停、恢复和停止操作对应在线程Thread的API就是suspend()、resume()和stop()。但是这些API是过期的，也就是不建议使用的。因为会导致程序可能工作在不确定状态下。

安全的中止则是其他线程通过调用某个线程A的interrupt()方法对其进行中断操作，被中断的线程则是通过线程通过方法isInterrupted()来进行判断是否被中断，也可以调用静态方法Thread.interrupted()来进行判断当前线程是否被中断，不过Thread.interrupted()会同时将中断标识位改写为false。

**13.** **sleep wait yield区别？wait如何唤醒**

sleep:**当前线程**休眠  不释放锁  （用于暂停线程）

wait:**当前线程**等待	释放锁   notify  notifyall   （用于线程交互）

yield:作用是让步。它能让当前线程由“运行状态”进入到“就绪状态”，从而让其它具有相同[优先级](https://so.csdn.net/so/search?q=优先级&spm=1001.2101.3001.7020)的等待线程获取执行权；但是，并不能保
证在当前线程调用yield()之后，其它具有相同优先级的线程就一定能获得执行权；也有可能是当前线程又进入到“运行状态”继续运行！ concurrenthashmap中initTable  不释放锁  （是当前线程让出cpu资源）

**14.** **sleep可中断吗？**

可以  Thread.sleep(xxx)  抓异常，如果中断就抛异常

**15.** **线程生命周期？**

Java中线程的状态分为6种：

\1. 初始(NEW)：新创建了一个线程对象，但还没有调用start()方法。

\2. 运行(RUNNABLE)：Java线程中将就绪（ready）和运行中（running）两种状态笼统的称为“运行”。

线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中，获取CPU的使用权，此时处于就绪状态（ready）。就绪状态的线程在获得CPU时间片后变为运行中状态（running）。

\3. 阻塞(BLOCKED)：表示线程阻塞于锁。

\4. 等待(WAITING)：进入该状态的线程需要等待其他线程做出一些特定动作（通知或中断）。

\5. 超时等待(TIMED_WAITING)：该状态不同于WAITING，它可以在指定的时间后自行返回。

\6. 终止(TERMINATED)：表示该线程已经执行完毕。

**16.** **ThreadLocal是什么？**

ThreadLocal是Java里一种特殊的变量。ThreadLocal为每个线程都提供了变量的副本，使得每个线程在某一时间訪问到的并非同一个对象，这样就隔离了多个线程对数据的数据共享。

在内部实现上，每个线程内部都有一个ThreadLocalMap，用来保存每个线程所拥有的变量副本。

**17.** **线程池原理是什么？**

在开发过程中，合理地使用线程池能够带来3个好处。

第一：降低资源消耗。第二：提高响应速度。第三：提高线程的可管理性。

1）如果当前运行的线程少于corePoolSize，则创建新线程来执行任务（注意，执行这一步骤需要获取全局锁）。

2）如果运行的线程等于或多于corePoolSize，则将任务加入BlockingQueue。

3）如果无法将任务加入BlockingQueue（队列已满），则创建新的线程来处理任务。

4）如果创建新线程将使当前运行的线程超出maximumPoolSize，任务将被拒绝，并调用RejectedExecutionHandler.rejectedExecution()方法。

**18.** **三个线程T1,T2,T3如何保证顺序执行？**

join()  T3中T2.join(T1.join())}   T1线程wait直到结束，唤醒T2，结束唤醒T3





---





### **Android**

#### **Activity**

**小技能get**

1.dialog弹出和下拉通知栏生命周期不变,若果是dialog主题的act，则会发生变化

2.为什么用bundle传递数据，不用hashmap。（bundle底层arraymap，android特有省内存，千以下的键值对用）

>  1.在数据量小的时候一般认为1000以下，当你的key为int的时候，使用SparseArray确实是一个很不错的选择，内存大概能节省30%，相比用HashMap，因为它key值不需要装箱，所以时间性能平均来看也优于HashMap,建议使用！
>  2.ArrayMap相对于SparseArray，特点就是key值类型不受限，任何情况下都可以取代HashMap,但是通过研究和测试发现，ArrayMap的内存节省并不明显，也就在10%左右，但是时间性能确是最差的，当然了，1000以内的数据量也无所谓了，加上它只有在API>=19才可以使用，个人建议没必要使用！还不如用HashMap放心。估计这也是为什么我们再new一个HashMap的时候google也没有提示让我们使用的原因吧。



**小拓展**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image127.png" alt="img" style="zoom:80%;" /> 



**弹窗Dialog和Toast对当前生命周期有影响吗？**

> 没。只有AMS才会改变生命周期，这个是WMS，WindowManger.addView，而且Dialog和Toast都属于SubWindow，需要附着在Window之上才能显示，也就是Activity中的phoneWindow

**启动Dialog主题或者透明主题的Activity对当前生命周期有影响吗？**

>  有。属于AMS，因为启动Activity是透明或者弹窗主题，所以原Activity只会执行OnPause方法，不会OnStop，还是“可见”状态。此场景下注意内存泄漏问题，在OnPause中执行停止ui更新逻辑，而不是OnStop或者Onstroy中，否则启动的Activity会泄漏

![image-20220809213959039](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220809213959039.png)







**BroadCast**

**系统原理**

**1.** **广播接收者通过binder往AMS中注册**

**2.** **广播发送者通过binder向AMS发送**

**3.** **AMS根据IntentFilter查找合适广播**

**4.** **AMS发送对应广播到消息列表中**

**5.** **广播接受者通过消息循环拿到广播，回调onReceive**

**源码分析见：https://blog.csdn.net/qq_35559358/article/details/78760053**



**本地广播原理**

**1.** **调用 sendBroadcast，传输广播 Intent**

**2.** **利用 Intent 中的Action 索引广播数组列表，索引出广播实体。**

**3.** **通过 Handler 回调到主线程，通过 executePendingBroadcasts 来运行广播。**

**4.** **调用注册的 BroadReciver 的 onReceive 方法来完成广播触发内容。**







**ContentProvider**

**简单实例：**

**进程A**

**1.** **创建数据库**

**2.** **继承ContentProvider，关联数据库，实现增删改查**

**3.** **配置文件中注册ContentProvider  exported=true**

**进程B**

**1.** **Uri访问provider，getContentResolver进行增删改查**



**View事件**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image128.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image129.png" alt="img" style="zoom:80%;" /> 





------

####  Fragment

**生命周期相关**

> **1:Activity如何通过生命周期去调用fragment的生命周期**
> 其实就是调用了fragmentManagerlmpli这个类来进行分发
> 发现最终都是走的dispatchStateChange(O;
> **2：发现fragment的生命周期状态只有5个**
> 通过降序以及升序来进行判断如果是升序，
> 走显示的生命周期
> **3:发现case里面没有break。**这样的好处，是为了让fragmenti走完整的生命周期



**commit() commitAllowingStateLoss() commitNow() commitNowAllowingStateLoss()区别**

> **commit()** 需要在宿主 Activity 保存状态之前调用，否则会报错。
>
> 这是因为如果 Activity 出现异常需要恢复状态，在保存状态之后的 commit() 将会丢失，这和调
>
> 用的初衷不符，所以会报错。
>
> **commitAllowingStateLoss()** 也是异步执行，但它的不同之处在于，允许在 Activity 保存状态之后调
>
> 用，也就是说它遇到状态丢失不会报错。因此我们一般在界面状态出错是可以接受的情况下使用它。
>
> **commitNow()** 是同步执行的，立即提交任务。
>
> 前面提到 FragmentManager.executePendingTransactions() 也可以实现立即提交事务。但我们一
>
> 般建议使用 commitNow() , 因为另外那位是一下子执行所有待执行的任务，可能会把当前所有的事务都
>
> 一下子执行了，这有可能有副作用。
>
> 此外，这个方法提交的事务可能不会被添加到 FragmentManger 的后退栈，因为你这样直接提
>
> 交，有可能影响其他异步执行任务在栈中的顺序。
>
> 和 commit() 一样， commitNow() 也必须在 Activity 保存状态前调用，否则会抛异常。













---

#### Service

<img src="https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20220926163221237.png" alt="image-20220926163221237" style="zoom:50%;" />



**Android8.0 startService有啥问题**

> app处于后台，startService会抛异常
> https://juejin.cn/post/6844903859710754823

**如何解决8.0上启动service的问题？**

> 1.判断高于8.0 ，前台启动
>
> ```
> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
>  activity.startForegroundService(intent);
> } else {
>  activity.startService(intent);
> }
> ```
>
> 但是前台启动服务必须在5s内调用startForeground显示通知栏，如果不想要咋办？在此服务中startForegroundService另一个服务，这个服务和它公用Notification ID，然后立马stopSelf，destroy中进行移除通知栏
>
> 2.光有上面代码，依然会崩溃，详见上面链接，可以采用JobIntentService
>
> https://www.freesion.com/article/49371161948/

**总结**

- **startService抛异常不是看调用的APP处于何种状态，而是看Servic所在APP处于何种状态，因为看的是UID的状态，所以这里重要的是APP而不仅仅是进程状态**
- 不要通过Handler延迟太久再startService，否则可能会有问题
- 应用进入后台，60s之后就会变成idle状态，无法start其中的Service，但是可以通过startForegroundService来启动
- Application里面不要startService，否则恢复的时候可能有问题
- startForGround 要及时配合startForegroundService，否则会有各种异常。
- 采用JobIntentService(待研究)





---







#### **Handler**

**Message**基于单链表实现

**1.** **内存泄漏**

**2.** **流程图**

**3.** **ThreadLocal**



**4.** **msg.next() 中 nativePollOnce(ptr,time) time为阻塞时间（距离下一条消息）**

补充：pipe机制，在没有消息时阻塞线程并进入休眠释放cpu资源，有消息时唤醒线程

Linux pipe/epoll机制，简单说就是在主线程的MessageQueue没有消息时，便阻塞在loop的queue.next()中的nativePollOnce() 方法里

①**阻塞了为什么还能继续响应用户操作呢**？

当系统收到来自因用户操作而产生的通知时, 会通过 Binder 方式从系统进程跨进程的通知我们的 application 进程中的 ApplicationThread,ApplicationThread又通过 Handler 机制往主线程的 messageQueue中插入消息，从而让主线程的loop()，Message msg = queue.next()这句代码可捕获一条 message ,然后通过 msg.target.dispatchMessage(msg)来处理消息,从而实现了整个 Android 程序能够响应用户交互和回调生命周期方法（具体实现ActivityThread 中的内部类H中有实现）

②**而至于为什么当主线程处于死循环的 Message msg = queue.next() 这句会阻塞线程的代码的时候不会产生 ANR 异常**

那是因为此时 messageQueue 中并没有消息，无需处理界面界面更新等操作。 因此主线程处于休眠状态，无需占用 cpu 资源， 而当 messageQueue 中有消息时,，系统会唤醒主线程，来处理这条消息。

③**那么我们在主线程中耗时为什么会造成 ANR 异常呢?**

那是因为我们在主线程中进行耗时的操作是属于在这个死循环的执行过程中, 如果我们进行耗时操作, 可能会导致这条消息还未处理完成,后面有接受到了很多条消息的堆积,从而导致了 ANR 异常.

④**一个Thread可以有几个Looper？几个Handler？**

一个线程只能有一个Looper，可以有多个Handler，在线程中我们需要调用Looper.perpare,他会创建一个Looper并且将Looper保存在ThreadLocal中，每个线程都有一个LocalThreadMap，会将Looper保存在对应线程中的LocalThreadMap，key为ThreadLocal，value为Looper

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image130.png" alt="img" style="zoom:50%;" /> 

⑤**handler内存泄漏如何解决**

- 静态内部类+弱引用

  private static class AppHandler extends Handler {
  //弱引用，在垃圾回收时，被回收
  WeakReference<Activity> activity;
  AppHandler(Activity activity){
  this.activity=new WeakReference<Activity>(activity);
  }
  public void handleMessage(Message message){
  switch (message.what){
  }
  }
  }

- removeCallbacksAndMessages

⑥**引用链**

主线程 —> threadlocal —> Looper —> MessageQueue —> Message —> Handler —> Activity

⑦**message是如何重复利用的（池化思想-对象优化）**

```
public static Message obtain() {
    synchronized (sPoolSync) {
        if (sPool != null) {
            Message m = sPool;
            sPool = m.next;
            m.next = null;
            m.flags = 0; // clear in-use flag
            sPoolSize--;
            return m;
        }
    }
    return new Message();
}
```

1. synchronized (sPoolSync)：给对象加锁，保证同一时刻只有一个线程使用Message。
2. if (sPool != null)：判断sPool链表是否是空链表，如果是空，就直接创建一个Message对象返回；否则就进入第三步。
3. 链表操作：将链表头节点移除作为重用的Message对象，第二个节点作为新链表（sPool ）的头节点。

<img src="https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20220920182733755.png" alt="image-20220920182733755" style="zoom:50%;" />



⑧**handler为什么可以切换线程**





---





#### **HandlerThread**

HandlerThread继承了Thread,是一种可以使用Handler的Thread；在run方法中通过looper.prepare()来开启消息循环，这样就可以在HandlerThread中创建Handler了；外界可以通过一个Handler的消息方式来通知HandlerThread来执行具体任务；确定不使用之后，可以通过quit或quitSafely方法来终止线程执行；具体使用场景是IntentService

```
@Override
public void run() {
    mTid = Process.myTid();
    Looper.prepare();
    synchronized (this) {
        mLooper = Looper.myLooper();
        notifyAll();
    }
    Process.setThreadPriority(mPriority);
    onLooperPrepared();
    Looper.loop();
    mTid = -1;
}
```

**考点：**

**notify  notifyAll  wait**



#### **IntentService**

onCreate:创建HandlerThread，拿到对应looper，创建handler

```
@Override
public void onCreate() {
    super.onCreate();
    HandlerThread thread = new HandlerThread("IntentService[" + mName + "]");
    thread.start();
    mServiceLooper = thread.getLooper();
    mServiceHandler = new ServiceHandler(mServiceLooper);//子线程looper
}
```

onStartCommand->onStart  handler发送消息，处理onHanldeIntent，stopSelf

```
@Override
public void onStart(@Nullable Intent intent, int startId) {
    Message msg = mServiceHandler.obtainMessage();
    msg.arg1 = startId;
    msg.obj = intent;
    mServiceHandler.sendMessage(msg);
}

private final class ServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            onHandleIntent((Intent)msg.obj);
            stopSelf(msg.arg1);
        }
}
```

使用：

```
/**
 * 这个IntentService虽然废弃，但是它的出现主要是避免线程的冲突，
 * 有些时候在使用Service的时候启动一个new Thread ，到头没stop掉就会持续占据，浪费资源
 */
public class MyIntentService extends IntentService {
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //这里自动启动一个线程进行操作，不会影响到主线程
        Log.d("MyIntentService","Thread String :" + Thread.currentThread().getId());//打印线程id就会发现和主线程是不一样的。
    }
}
```
**IntentService与Service的区别**

从属性 & 作用上来说 Service：依赖于应用程序的主线程（不是独立的线程）

不建议在Service中编写耗时的逻辑和操作，否则会引起ANR；

IntentService：创建一个工作线程来处理多线程任务 　　

Service需要主动调用stopSelft()来结束服务，而IntentService不需要（在所有intent被处理完后，系统会自动关闭服务）

---



#### Binder

**定义：**

在Android中我们所使用的Activity，Service等组件都需要和AMS(system_server)

通信，这种跨进程的通信都是通过Binder完成。

-  机制：Binder是一种进程间通信机制；
-  驱动：Binder是一个虚拟物理设备驱动；
-  应用层：Binder是一个能发起通信的Java类；

**多进程优势：**

虚拟机分配给各个进程的运行内存是有限制的，LMK也会优先回收对

系统资源的占用多的进程。

-  突破进程内存限制，如图库占用内存过多；
-  功能稳定性：独立的通信进程保持长连接的稳定性；
-  规避系统内存泄漏: 独立的WebView进程阻隔内存泄露导致的问题；
-  隔离风险：对于不稳定的功能放入独立进程，避免导致主进程崩溃；



**ipc对比**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220802210943550.png" alt="image-20220802210943550" style="zoom:60%;" />

实名服务：AMS 、 WMS（系统服务）

匿名服务：一般自己的服务

区别是：是否在ServiceManager中注册，如果注册实名，否则匿名

**aidl流程**

![image-20220804212305034](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220804212305034.png)

**binder原理**

![binder原理](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/binder%E5%8E%9F%E7%90%86.png)

用户态切换内核态，上下文切换，类似多个线程之间的切换，会保存当前运行的状态，比较耗时耗资源

跨进程socket传递数据过程：进程1用户空间copy到内核空间，因为内核共享，所以进程2可以直接copy到自己的用户空间，两次拷贝



> mmap：Linux通过将一个虚拟内存区域与一个磁盘上的对象(文件)关联起来，以初始化这个虚拟内存区域的内容，这个过 程称为内存映射(memory mapping)。联想：MMKV

Binder传递数据过程：少了一次拷贝，直接将进程2的用户空间中一块区域mmap映射到内核空间中,如下。

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220802214942593.png" alt="image-20220802214942593" style="zoom:67%;" />

**为什么需要Binder机制**

> ipc有管道，socket，信号量，共享空间等
>
> **性能角度** 管道 消息队列 套接字都需要两次数据拷贝，共享内存方式一次拷贝都不需要，Binder也只需要拷贝一次，其性能仅次于共享内存稳定性角度 Binder基于CS架构，Server与Client断相对独立，稳定性较好。共享内存实现方式复杂且没有客户与服务端之别，需要充分考虑到访问临界并发同步问题。
>
> **安全角度**传统的IPC对于通信双方的身份没有做出严格的验证，使用传统IPC机制，只能由客户在数据包内填入UID/PID,不可靠.Android为每个安装好的应用程序分配了自己的UID，故进程的UID是鉴别进程身份的重要标志，Android系统只向外暴露Client端，Client端将任务发送给Server端，根据权限控制策略，判断PID与UID是否符合权限。

**疑问：那么为什么发送方不进行mmap呢？**

> 因为如果发送方也进行mmap，那就是共享内存的方式了，性能好了，但是使用起来很复杂，死锁数据不同步等问题。

**intent传递数据大小**

> mmap  内存映射  1M-8k，其实到不了那么多，预留打包

**谈谈你对 binder 的理解**

> binder 是 Android 中主要的跨进程通信方式，binder 驱动和 service manager 分别相当于网络协议中的路由器和 DNS，并基于 mmap 实现了 IPC 传输数据时只需一次拷贝。
>
> binder 包括 BinderProxy、BpBinder 等各种 Binder 实体，以及对 binder 驱动操作的 ProcessState、IPCThreadState 封装，再加上 binder 驱动内部的结构体、命令处理，整体贯穿 Java、Native 层，涉及用户态、内核态，往上可以说到 Service、AIDL 等，往下可以说到 mmap、binder 驱动设备，是相当庞大、繁琐的一个机制。

**怎么理解页框和页**

> 页框是指一块实际的物理内存，页是指程序的一块内存数据单元。一个页框可以映射给多个页，也就是说一块实际的物理存储空间可以映射给多个进程的多个虚拟内存空间，这也是 mmap 机制依赖的基础规则。

**简单说下 binder 的整体架构吧**

> ​	Client 通过 ServiceManager 或 AMS 获取到的远程 binder 实体，一般会用 **Proxy** 做一层封装，比如 ServiceManagerProxy、 AIDL 生成的 Proxy 类。而被封装的远程 binder 实体是一个 **BinderProxy**。
>
> ​	**BpBinder** 和 BinderProxy 其实是一个东西：远程 binder 实体，只不过一个 Native 层、一个 Java 层，BpBinder 内部持有了一个 binder 句柄值 handle。
>
> ​	**ProcessState** 是进程单例，负责打开 Binder 驱动设备及 mmap；**IPCThreadState** 为线程单例，负责与 binder 驱动进行具体的命令通信。
>
> ​		由 Proxy 发起 transact() 调用，会将数据打包到 Parcel 中，层层向下调用到 BpBinder ，在 BpBinder 中调用 IPCThreadState 的 transact() 方法并传入 handle 句柄值，IPCThreadState 再去执行具体的 binder 命令。
>
> ​		由 binder 驱动到 Server 的大概流程就是：Server 通过 IPCThreadState 接收到 Client 的请求后，层层向上，最后回调到 **Stub** 的 onTransact() 方法。
>
> ​		当然这不代表所有的 IPC 流程，比如 Service Manager 作为一个 Server 时，便没有上层的封装，也没有借助 IPCThreadState，而是初始化后通过 binder_loop() 方法直接与 binder 驱动通信的。



#### View绘制









#### Recyclerview



---





#### AMS



##### Android 系统启动流程

<img src="https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20220916100306710.png" alt="image-20220916100306710" style="zoom:30%;" />

- 开机加电后，CPU先执行**预设代码**、加载ROM中的引导程序**Bootloader**和Linux内核到RAM内存中去，然后初始化各种软硬件环境、加载驱动程序、挂载根文件系统，执行**init进程**。
- init进程会启动各种系统本地服务，如**SM**（ServiceManager）、MS（Media Server）、bootanim（开机动画）等，然后init进程会在解析init.rc文件后fork()出**Zygoto进程**。
- Zygote会启动Java虚拟机，通过jni进入Zygote的java代码中，并创建**socket**实现IPC进程通讯，然后启动**SS**（SystemServer）进程。
- SS进程负责启动和管理整个framework，包括**AMS**（ActivityManagerService）、**WMS**（WindowManagerService）、PMS（PowerManagerService）等服务、同时启动binder线程池，当SS进程将系统服务启动就绪以后，就会通知AMS启动Home。

```
SystemServer
如何启动AMS
system_server 进程中 启动AMS，startService方法很简单，是通过传进来的class然后反射创建对应的service服务。所以此处创建的是 Lifecycle的实例， 然后通过startService启动了AMS服务
public static void main(String[] args) {
	new SystemServer().run();
}

private void run() {
	startBootstrapServices();  //启动了AMS
	startCoreServices();
	startOtherServices();
}

private void startBootstrapServices() {
	mActivityManagerService = mSystemServiceManager.startService(ActivityManagerService.Lifecycle.class).getService();
	mActivityManagerService.setSystemServiceManager(mSystemServiceManager);
	mActivityManagerService.setInstaller(installer);
	mActivityManagerService.initPowerManagement();
	mActivityManagerService.setSystemProcess();
}
```

- AMS通过Intent隐式启动的方式启动**Launcher**，Launcher根据已安装应用解析对应的xml、通过findBiewById()获得一个RecycleView、加载应用图标、最后成功展示App列表。

**Zygote 为什么不采用 Binder 机制进行 IPC 通信？**

> Binder 机制中存在 Binder 线程池，是多线程的，如果 Zygote 采用 Binder 的话就存在上面说的 fork() 与 多线程的问题了。其实严格来说，Binder 机制不一定要多线程，所谓的 Binder 线程只不过是 在循环读取 Binder 驱动的消息而已，只注册一个 Binder 线程也是可以工作的，比如 service manager 就是这样的。实际上 Zygote 尽管没有采取 Binder 机制，它也不是单线程的，但它在 fork() 前主动停止 了其他线程，fork() 后重新启动了。





##### launcher(app)启动流程

<img src="https://upload-images.jianshu.io/upload_images/9601136-3fd506a10d612d4e.png?imageMogr2/auto-orient/strip|imageView2/2/w/960/format/webp" alt="img" style="zoom:67%;" />

- 当你点击了App的桌面图标时，Luncher进程收到你的操作。启动开始。
- Luncher进程启动远程进程，通过Binder发消息给system_server。
- system_server 中AMS经过一系列复杂操作，最终调用 Process.start(android.app.ActivityThread) , 然后通过socket通知Zygote进程。
- Zygote进程收到通知，fork出app进程，并执行ActivityThread.main()方法。
- ActivityThread.main()主要干了三件事：

①一准备主线程Looper ,  并使其loop()轮询事件，而且还实例化了ActivityThread

```
android.app.ActivityThread#main
Looper.prepareMainLooper();
ActivityThread thread = new ActivityThread();
thread.attach(false, startSeq);
Looper.loop();
```

②并调用器**attach**方法，跨进程调用AMS中方法，经过一系列处理，又跨进程ApplicationThread的**bindApplication**发送handler给ActivityThread进行处理，反射创建Application实例，并调用application的**attach**（attachbase）和**oncreate**方法。

```
private void attach(boolean system, long startSeq) {//activityThead
			final IActivityManager mgr = ActivityManager.getService();
		    mgr.attachApplication(mAppThread, startSeq);
}

（applicatoin）thread.bindApplication(args...);  //AMS中

 private void handleBindApplication(AppBindData data) { //activityThread
	   app = data.info.makeApplication(data.restrictedBackupMode, null);    //--------->Application-attachBaseContext()  反射创建application
        if (!ArrayUtils.isEmpty(data.providers)) {
            installContentProviders(app, data.providers);                //--------->ContentProvider-onCreate()
        }
        mInstrumentation.callApplicationOnCreate(app);                   //--------->Application-onCreate()
}
```

③接着需要创建第一个Activity，也就是主Activity

```
mAtmInternal.attachApplication(app.getWindowProcessController());
最后执行到
realStartActivityLocked
clientTransaction.addCallback(LaunchActivityItem.obtain(new Intent(r.intent)...)
LaunchActivityItem#execute
public void execute(ClientTransactionHandler client, IBinder token,PendingTransactionActions pendingActions) {
        client.handleLaunchActivity(r, pendingActions, null /* customIntent */);
}
```

后续就是act的启动了

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220915222326532.png" alt="image-20220915222326532" style="zoom: 80%;" />

注意：

> App启动可以参考https://www.jianshu.com/p/3c62c5c5668d，
>
> Activity启动可以参考https://www.jianshu.com/p/1969ef6e545d
>
> 从系统启动一直到Activity渲染可以参考https://mp.weixin.qq.com/s/5mP_stp_nWQfBRo_keAPSg



**面试题**

**Application, Activity, ContentProvider启动顺序**

> Application->attachBaseContext =====>ContentProvider->onCreate =====>Application->onCreate =====>Activity->onCreate
>
> 从下面的分析可以看出，Provider的初始化实际上是在Application的初始化过程中发生的，而Activity是在这之后。
>
> <img src="https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20220928210822418.png" alt="image-20220928210822418" style="zoom:30%;" />

```
//ActivityThread
private void handleBindApplication(AppBindData data) {
        app = data.info.makeApplication(data.restrictedBackupMode, null);    //--------->Application-attachBaseContext()
        if (!ArrayUtils.isEmpty(data.providers)) {
            installContentProviders(app, data.providers);                //--------->ContentProvider-onCreate()
        }
        mInstrumentation.callApplicationOnCreate(app);                   //--------->Application-onCreate()
}

//LoadedApk
 public Application makeApplication(boolean forceDefaultAppClass,
        Instrumentation instrumentation) {
        app = mActivityThread.mInstrumentation.newApplication(   //-------->反射出application 并 attachBaseContext()
                cl, appClass, appContext); 
        appContext.setOuterContext(app);
     if (instrumentation != null) {
        instrumentation.callApplicationOnCreate(app);            //--------->instrumentation为空，不走onCreate()
        }
    }
    return app;
}
```

```
handleLaunchActivity-performLaunchActivity-callActivityOnCreate
```

**如何判断APP是否已经启动？**

>  AMS会保存一个ProcessRecord信息，有两部分构成，“uid + process”，每个应用工程序都有自己的uid，而process就是AndroidManifest.xml中Application的process属性，默认为package名。每次在新建新进程前的时候会先判断这个 ProcessRecord 是否已存在，如果已经存在就不会新建进程了，这就属于应用内打开 Activity 的过程了





##### startAtivity源码分析

https://www.jianshu.com/p/1969ef6e545d

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image120.png" alt="img" style="zoom:60%;" /> 

> **realStartActivityLocked** 和**App启动中**后半段一样，然后通过一些操作，最终通过ClientTransaction.addCallback时添加的LaunchActivityItem中的execute()方法回调了ActivityThread中的handleLaunchActivity()方法，进而实例化了Activity(ActivityRecord)和Application



##### **setContent源码**

**1 添加view**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image121.png" alt="img" style="zoom:80%;" /> 

**2 渲染view（先addview添加到window，然后performTraversal绘制）**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image122.png" alt="img" style="zoom:40%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image123.png" alt="img" style="zoom:70%;" /> 



**3 Measure （看表格）**

**注：若view没有重写onMeasure方法，那match 和wrap都走父布局的可用空间**<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image124.png" alt="img" style="zoom:80%;" />

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image125.png" alt="img" style="zoom:40%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image126.png" alt="img" style="zoom:40%;" /> 

**终极大招：**

**Activity，DecorView，PhoneWindow和ViewRoot的作用和相关关系**

[**https://blog.51cto.com/u_14813744/2718692**]

https://mp.weixin.qq.com/s/6tEBj9b-Uuw7vT39infiAA



---



#### WMS

**注意：WMS内容基于AMS中的setCotentView中**

###### 概念

window：Toast  Dialog  Activity中都有window，显示

windowmanager：对window的管理

wms：window最终管理者，启动、添加、删除等

###### WMS启动

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220924181252682.png" alt="image-20220924181252682" style="zoom:50%;" />

同AMS启动，startOtherServices

```
SystemServer
private void startOtherServices(@NonNull TimingsTraceAndSlog t) {
		wm = WindowManagerService.main(context, inputManager, !mFirstBoot, mOnlyCore,
        	new PhoneWindowManager(), mActivityManagerService.mActivityTaskManager);
}
WindowManagerService
public static WindowManagerService main() {
        return main(context, im, showBootMsgs, onlyCore, policy, atm,
                SurfaceControl.Transaction::new, Surface::new, SurfaceControl.Builder::new);
}
 public static WindowManagerService main() {
        DisplayThread.getHandler().runWithScissors(() ->
                sInstance = new WindowManagerService(context, im, showBootMsgs, onlyCore, policy,
                        atm, transactionFactory, surfaceFactory, surfaceControlFactory), 0);
        return sInstance;
    }
```



**window分类：**

> Application Window：应用程序窗口，Activity  （1-99）
>
> Sub Window：子窗口，不能独立存在，必须附着在其他窗口才可以，PopupWindow（Dialog好像不是）（1000-1999）
>
> System Window：系统窗口，输入法、音量条 （2000-2999）
>
> 窗口次序  层级越大，越靠前

**phonewindow何时创建**

> AMS中launch启动后，performLaunchActivity中反射创建activity，并且attach进行创建phonewindow

```
ActivityThread#performLaunchActivity
private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent) {
 			activity = mInstrumentation.newActivity(
                      cl, component.getClassName(), r.intent);    //反射得到activity
      activity.attach(appContext, this, getInstrumentation(), r.token,
                        r.ident, app, r.intent, r.activityInfo, title, r.parent,
                        r.embeddedID, r.lastNonConfigurationInstances, config,
                        r.referrer, r.voiceInteractor, window, r.configCallback,
                        r.assistToken);
}
activity#attach
final void attach(Context context, ...){
			mWindow = new PhoneWindow(this, window, activityConfigCallback); //创建phonewindow
}
```

**viewRootImpl什么时候创建**

windowmanagerimpl.addview-》windowmangerglobal.addview中

```
WindowMangerGlobal#addView
 public void addView(View view, ...) {
		root = new ViewRootImpl(view.getContext(), display);
		mViews.add(view);//global中所有decorview集合
        mRoots.add(root);//global中所有viewRootImpl集合  
		root.setView(view, wparams, panelParentView, userId);
}
```

一个应用只会创建一个WindowMangerGlobal，ViewRootImpl和DecorView一一对应

###### **更新window**

```
WindowManagerGlobal#updateViewLayout
public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
        final WindowManager.LayoutParams wparams = (WindowManager.LayoutParams)params;
        view.setLayoutParams(wparams);
        synchronized (mLock) {
            int index = findViewLocked(view, true);
            ViewRootImpl root = mRoots.get(index);
            mParams.remove(index);
            mParams.add(index, wparams);
            root.setLayoutParams(wparams, false);
        }
}
ViewRootImpl#setLayoutParams
void setLayoutParams(WindowManager.LayoutParams attrs, boolean newView) {
   		scheduleTraversals(); //刷新
}
```

![image-20220924122812641](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220924122812641.png)





###### 事件分发

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220924121410281.png" alt="image-20220924121410281" style="zoom:33%;" />

**为什么DecorView不直接分发给ViewGroup?**

> 可能分发之前还需要特殊处理，而ViewGroup不具备这些，比如打电话过程中，脸碰到手机不响应时间等



###### 刷新流程

![image-20220924172005380](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220924172005380.png)

![image-20220924171939726](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220924171939726.png)

屏幕管理核心DisplayContent

![image-20220924213110033](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220924213110033.png)

SurfaceFlinger

将多块画面合成一个,如手机状态栏+内容+导航











###### 面试题

**onResume中测量宽高有效吗？那应该如何测量**

> 无效，因为decorview是在onResume后才去addview的
>
> view.post(new Runnable)  ，ViewRootImpl的performTravels中
>
> getRunQueue().executeActions(xxx),xxx==上面的Runnable，

**子线程可以更新ui吗？**

> 可以，执行快就行，没来得及到addview中检查线程

**Activity，Window，View三者关系**

> Activity是界面交互、业务逻辑、生命周期管理
>
> Window是显示的区域，view的载体
>
> View显示控件

**首次View绘制流程在什么时候**

> handlerOnResume中执行onResume后，WindowmangerImpl.addview  ----ViewRootImpl.performTravels----measure,layout,draw

**invalidate会马上进行屏幕刷新吗**

> 不会，申请，等下一个同步信号才会刷新

**为什么主线程耗时会导致掉帧**

> 会影响下一帧绘制





---



#### **动画**

##### **属性动画**

**Interpolator（插值器）**

主要作用是可以根据时间流逝的百分比来计算当前属性值的百分比，控制动画的变化速率

AccelerateDecelerateInterpolator 默认差值器



**TypeEvaluator****（****估值器****）**

作用是定义从初始值过渡到结束值的计算规则

Demo：

1郭霖的PointA-PointB  https://blog.csdn.net/guolin_blog/article/details/43816093

2咱项目中的tab底部导航（没有用到，可以参考）



##### **MotionLayout**

连接布局过渡与复杂的手势处理。你可以把它想象成属性动画框架、过渡动画管理和CoordinatorLayout三种能力集于一身的框架。



##### 方案对比

目前较常见的动画实现方案有原生动画、帧动画、gif/webp、lottie/SVGA、cocos引擎，对于复杂动画特效的实现做个简单对比

| 方案        | 实现成本                             | 上手成本 | 还原程度           | 接入成本 |
| ----------- | ------------------------------------ | -------- | ------------------ | -------- |
| 原生动画    | 复杂动画实现成本高                   | 低       | 中                 | 低       |
| 帧动画      | 实现成本低，但资源消耗大             | 低       | 中                 | 低       |
| gif/webp    | 实现成本低，但资源消耗大             | 低       | 只支持8位颜色      | 低       |
| Lottie/SVGA | 实现成本低，部分复杂特效不支持       | 低       | 部分复杂特效不支持 | 低       |
| cocos2d引擎 | 实现成本高                           | 高       | 较高               | 较高     |
| AlphaPlayer | 开发无任何实现成本，一次接入永久使用 | 低       | 高                 | 低       |

**而在复杂动画特效高效实现的场景中，我们的备选方案会更少一些，可以将讨论集中在Cocos2d、Lottie、Webp和本文的AlphaPlayer上。**

**Lottie**

Lottie是非常优选的多平台动画效果解决方案，其简单原理是将AE动画导出的json文件解析成每个layer层级对象，再绘制成对应的Drawable，最后显示在View上。在不涉及mask和mattes等特性时性能非常优选，主要耗时基本集中在Canvas#draw()上而已，json解析时通过流读取的方式避免一次性加载全部json数据带来的OOM问题。

但是也存在部分不足：

1. 如果动画涉及到mask或mattes等特性时，需要生成2~3个临时bitmap实现动画效果，容易引起内存抖动，且涉及的canvas#saveLayer()和canvas#drawBitmap()会带来额外的耗时；
2. 如果动画中还直接使用了图片，在ImageAssetManager首次对图片解码是在主线程进行的（据了解在iOS的版本上，使用图片导致内存和CPU的性能消耗会更大）；
3. 主要耗时还是在draw()上，绘制区域越大耗时越长；
4. 目前支持的AE特性还有待完善，此外有一些特性在低版本上可能还会没有效果，设计资源时需要规避。（[Supported After Effect Features](http://airbnb.io/lottie/#/supported-features)）

实际使用过程中，最常见的首启全屏引导动画基本都会包含上面提到的前三点不足，这种情况下性能其实是大幅退化的。因此对于直播场景的复杂特效动画而言，Lottie就不是一个很合适的实现方案了。

**Cocos2d-x**

Cocos2d-x支持非常多的游戏功能，诸如精灵、动作、动画、粒子特效、骨骼动画等等，可以供开发者自由实现各种姿势的动画效果，再加上自身具备跨平台能力和轻量级，同时支持Lua作为开发语言，可以说是非常适合植入移动端作为动画效果实现方案的游戏引擎。

但实际使用维护中会面临很多问题：

1. 体积大，即使经过裁剪也还有2M左右的大小，如果不是核心场景需要基本很难允许接入；
2. 对开发者的技术栈有较高要求；
3. 无法满足快速迭代；
4. 维护难度大，尤其是在Android机型兼容的问题上。

**Webp**

Webp相比PNG和JPEG格式体积可以减少25%，在移动端的平台支持上也很全面，支持24位RGB色；缺点是资源体积比较大，而且使用的软解效率低下，低端机上有明显卡顿问题。

**AlphaPlayer**

相比于上面提到的几个方案，AlphaPlayer的接入体积极小（只有40KB左右），而且对动画资源的还原程度极高，资源制作时不用考虑特效是否支持的问题，对开发者和设计师都非常友好。通过接入ExoPlayer或者自研播放器可以解决系统播放器在部分机型上可能存在的兼容性问题，且能带来更好的解码性能。

---



####  其他

#####  设备唯一id获取

imei在6.0申请权限，Android 9.0以后彻底禁止第三方应用获取设备的IMEI（即使申请了 READ_PHONE_STATE 权限）。所以，如果是新APP，不建议用IMEI作为设备标识；

mac地址10.0后的地址也放弃了，不能读取mac地址

解决方案：多个硬件设备属性id组合成一个id

> AndroidId : 如：df176fbb152ddce,无需权限,极个别设备获取不到数据或得到错误数据；
>
> serial：如：LKX7N18328000931,无需权限,极个别设备获取不到数据；
>
> IMEI : 如：23b12e30ec8a2f17，需要权限；
>
> Mac: 如：6e:a5:....需要权限，高版本手机获得数据均为 02:00.....（不可使用）
>
> Build.BOARD  如：BLA  主板名称,无需权限,同型号设备相同Build.BRAND  如：HUAWEI  厂商名称,无需权限,同型号设备相同Build.HARDWARE  如：kirin970  硬件名称,无需权限,同型号设备相同

如果觉得手机升级后不够稳定，跟之前id不一致，可以采用移动统一联盟sdk miit_mdid**



##### android12适配存储权限

**以下不需要权限**

> 内促存储沙盒  getData下
>
> 如 getCacheDir ------ /data/data/com.xxx/cache   如glide默认缓存路径
>
> 外部存储沙盒 getExternalFilesDir
>
> 如 getExternalCacheDir() ----/storage/emulated/0/Android/data/com.learn.test/cache 咪咕小游戏

**以下需要权限，卸载不会消失**

> 外部存储  getExternalStorageDirectory



##### **MultixDex**

默认只有一个dex，但是方法数会超65535，打开multixenable开关进行分包，但是5.0以下不支持分包，所以谷歌出了解决方案，application中`MultiDex.install(base)`，启动比较耗时如何进行优化？

> **5.0以下这个dexElements 里面只有主dex（可以认为是一个bug），没有dex2、dex3...，MultiDex是怎么把dex2添加进去呢?** 答案就是反射`DexPathList`的`dexElements`字段，然后把我们的dex2添加进去，当然，dexElements里面放的是Element对象，我们只有dex2的路径，必须转换成Element格式才行，所以**反射DexPathList里面的makeDexElements 方法**，将dex文件转换成Element对象即可。

**原理：**

> 通过反射dexElements数组，将新增的dex添加到数组后面，这样就保证ClassLoader加载类的时候可以从新增的dex中加载到目标类，经过分析后最终MultipDex原理图如下：

<img src="https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20220928210558901.png" alt="image-20220928210558901" style="zoom:50%;" />

**那如何进行优化Multidex的install速度呢？**

> 1.子线程install（不推荐），会报启动页缺失的class错误，还有contentprovider的缺失，因为这个启动比较早，如果没在主multidex就会报错，虽然可以指定哪些类在主dex，但是还是不靠谱，难维护
>
> ```java
>  defaultConfig {
>      //分包，指定某个类在main dex
>      multiDexEnabled true
>      multiDexKeepProguard file('multiDexKeep.pro') // 打包到main dex的这些类的混淆规制，没特殊需求就给个空文件
>      multiDexKeepFile file('maindexlist.txt') // 指定哪些类要放到main dex
>  }
> ```
>
> maindexlist.txt 文件指定哪些类要打包到主dex中，内容格式如下
>
> ```arduino
> com/lanshifu/launchtest/SplashActivity.class
> ```
>
> 2.今日头条方案
>
> - 在主进程Application 的 attachBaseContext 方法中判断如果需要使用MultiDex，则创建一个临时文件，然后开一个进程（LoadDexActivity），显示Loading，异步执行MultiDex.install 逻辑，执行完就删除临时文件并finish自己。
> - 主进程Application 的 attachBaseContext 进入while代码块，定时轮循临时文件是否被删除，如果被删除，说明MultiDex已经执行完，则跳出循环，继续正常的应用启动流程。
> - 注意LoadDexActivity 必须要配置在main dex中。









---





#### **Kotlin**

\1. var val 等

\2. if  when for

\3. 关键字：open,data,object

\4. listOf("a","b","c")  不可变  setOf()

\5. mutableListOf("a","b","c") 可变  mutableSetof()

\6. map["a"] = 1  或者   mapOf("a" to 1)

遍历 for((a,b) in map)

\7. lambda

\8. 可空?.   ?:判断   !!非空断言  let

\9. $字符串

\10. 函数参数默认值  主构造

\11. with run apply

\12. 静态方法 4种（前两种山寨）

\13. lateinit

\14. 扩展函数

\15. 运算符重载  +  -

\16. 高阶函数  vararg  （看demo）

\17. infix  自定义函数  has

\18. 泛型实化，out 协变（? extends Object）生成者    in 逆变（? super String）消费者

out:只能获取             in：只能修改

{n:T->xx}  等价于   fun(n:T)=xx  等价于fun(n):String{return xx}



##### Flow

为什么引入`Flow`，我们可以从`Flow`解决了什么问题的角度切入

> 1. `LiveData`不支持线程切换，所有数据转换都将在主线程上完成，有时需要频繁更改线程，面对复杂数据流时处理起来比较麻烦
> 2. 而`RxJava`又有些过于麻烦了，有许多让人傻傻分不清的操作符，入门门槛较高，同时需要自己处理生命周期，在生命周期结束时取消订阅

特点

- `Flow` 支持线程切换、背压
- `Flow` 入门的门槛很低，没有那么多傻傻分不清楚的操作符
- 简单的数据转换与操作符，如 `map` 等等
- 冷数据流，不消费则不生产数据,这一点与`LiveData`不同：`LiveData`的发送端并不依赖于接收端。
- 属于`kotlin`协程的一部分，可以很好的与协程基础设施结合





CallBack

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image132.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image133.png" alt="img" style="zoom:80%;" /> 

Handler的callback

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image134.png" alt="img" style="zoom:80%;" /> 

---



### **JetPack**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image135.png" alt="img" style="zoom:80%;" /> 

前戏

dagger2 解耦  （快递员案例） -> 难用 ->引出二次封装hilt

1  局部单例  全局单例

2  原理  mainact.stu = stuprovider.get()



##### **Lifecycle**

###### **使用**

Androidx中activity已经实现了关于生命周期的监听封装

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image136.png" alt="img" style="zoom:60%;" /> 

**被观察者**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image137.png" alt="img" style="zoom:80%;" /> 

**观察者**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image138.png" alt="img" style="zoom:60%;" /> 

疑问：Activity的生命周期是如何通知到LifeCyclerObserver中的？

请看下面源码解析

###### **源码解析**

点击事件中lifecycle.addObserver(NetworkStateManager.instance)，那么应该先从第234步开，生命周期绑定回调，mState = RESUME

1．addObserver<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image139.png" alt="img" style="zoom:80%;" />

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image140.png" alt="img" style="zoom:80%;" /> 

注解的观察者调用的是反射观察者类，继承之LifecycleEventObserver

或者封装好的FullLifecycleObserver则直接回调onStateChaged

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image141.png" alt="img" style="zoom:80%;" /> 

目前是OnResume状态的话，则执行OnCreate-OnStart-OnResume

2．在ComponentActivity中onCreate创建空白ReportFragment（类似Glide）

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image142.png" alt="img" style="zoom:80%;" /> 

生命周期回调中都会进行dispatch(event)

3．状态机（根据被观察者生命周期获取对应状态，然后同步到观察者中）

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image143.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image144.png" alt="img" style="zoom:80%;" /><img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image145.png" alt="img" style="zoom:80%;" />



4． 同步状态

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image146.png" alt="img" style="zoom:60%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image147.png" alt="img" style="zoom:80%;" /> 

举例：倒退

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image148.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image149.png" alt="img" style="zoom:80%;" /> 



5． 通知更新

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image150.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image151.png" alt="img" style="zoom:80%;" /> 

**疑问1**：Lifecycle为什么要设计状态state呢？

状态机给其他框架使用，比如LiveData，VideModel

**疑问2**：如果在onResume中addOnserver呢？

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image152.png" alt="img" style="zoom:80%;" /> 

观察者初始状态为INITIALIZED，观察者Resume状态，根据图，onCreate-onResume执行

**疑问3**：onStop中add

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image153.png" alt="img" style="zoom:80%;" /> 

onStop后状态为Created,然后执行add方法，从初始状态开始，到onCreated

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image154.png" alt="img" style="zoom:80%;" />onDestroy状态更新为DESTROYED倒退，然后执行Ondestroy

add一直往前走

**疑问4**：onCreate中和onResume中add有啥区别？

虽然观察者都能执行onCreate回调，但是却不能和Activity中onCreate时机一致，所以最好还是onCreate中进行add监听



几种区别：

ComponentActivity默认add了一个监听

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image155.png" alt="img" style="zoom:80%;" /> 



以上源码基于最基本的LifecycleEventObserver，会走到注解反射方法，

比较麻烦，通常用以下方式

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image156.png" alt="img" style="zoom:80%;" /> 



LifecycleObserver 注解反射		  比较原始 ViewDataBinding中OnStartListener

LifecycleEventObserver onStateChanged   ComponentActivity

DefaultLifecycleObserver 回调生命周期    常用简单





---





##### **LiveData**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image157.png" alt="img" style="zoom:80%;" /> 

###### **使用**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image158.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image159.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image160.png" alt="img" style="zoom:80%;" /> 

代替了eventbus



粘性缺点：

历史发送的数据不想关心，只关心订阅后的数据变化

比如ActivityB中接受数据，ActivityA中发送了数据，跳转过来就会变化，但是我不想要这种效果。（防止其他页面不可控）

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image161.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image162.png" alt="img" style="zoom:80%;" /> 



###### **源码解析**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image163.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image164.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image165.png" alt="img" style="zoom:80%;" /> 

activeStateChanged(shouldBeActive()) 如果是resume或者start 就是active = true

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image166.png" alt="img" style="zoom:80%;" /> 





<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image167.png" alt="img" style="zoom:80%;" /> 

简单总结：

粘性事件：

A中setValue先发送，保存数据到mData，并mVersion++为0

B中observe（被观察者，观察者）建立两者绑定为->LifecycleBoundObserver实现LifecycleEventObserver接口，可以监听生命周期变化，如果是started或者resume，则active为true，判断观察者是否为最新版本，不是则回调观察者onChange方法



非粘性事件：

observer进行两者绑定，同B，可以监听生命周期变化，如果active为true（started或者resume状态，走了started就不会再走resume的activeStateChange里面代码，会判断上次状态是否相同），那么执行版本号判断，发现并没有最新版本，则不做处理。

setValue会查找所有绑定该数据的观察者，遍历，判断版本号进行回调onChange方法。

针对非粘性，如果以上是B，A中也进行数据观察，A跳转B，在B中setValue，那么遍历所有跟数据绑定的观察者，A不会收到事件，因为A的active为false



知识点：

postValue 子线程调用 通过主线程的Handler切换，执行setValue

**解决粘性：**

> 1   observe订阅的时候，反射hook， mLastVersion=mVersion
>
> 2  UnPeekLiveData



**LiveData不足**

> 1.`LiveData`只能在主线程更新数据
> 2.`LiveData`的操作符不够强大,在处理复杂数据流时有些捉襟见肘
>
> 由此引入flow来代替LiveData





---







##### **ViewBinding**

原理就是Google在那个用来编译的gradle插件中增加了新功能，当某个module开启ViewBinding功能后，编译的时候就去扫描此模块下的layout文件，生成对应的binding类，最终使用的仍然是findViewById，和ButterKnife异曲同工，不同的是ButterKnife通过编译时注解生成ViewBinding类，而ViewBinding是通过编译时扫描layout文件生成ViewBinding类。

效率比DataBinding高很多倍，谷歌支持，只要gradle中打开开关即可

Databingding用的APT编译时技术，比较慢

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image168.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image169.png" alt="img" style="zoom:80%;" /> 

新建布局后，立马生成对象类

Kotlin中有个插件，基本弃用，可能会引用到其他act中控件，崩溃，有隐患

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image170.png" alt="img" style="zoom:80%;" /> 







---





##### **DataBinding**

注意：项目越大，可能会越卡，所以还是建议用MVP

###### <img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image171.png" alt="img" style="zoom:80%;" />**使用**

Java版本

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image172.png" alt="img" style="zoom:80%;" /> 





<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image173.png" alt="img" style="zoom:80%;" /><img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image174.png" alt="img" style="zoom:80%;" />

@=  支持view驱动model

Kt版本

bean自动有get set，所以不好加代码，要换种方式

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image175.png" alt="img" style="zoom:80%;" /> 

###### **原理**

1 DataBindingUtil.setContentView

利用APT技术生成代码

将通过tag取出的View与

ActivityMainBindingImpl中对应的View属性进行绑定 会返回一个Object[] bindings数组



2 Module->View

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image176.png" alt="img" style="zoom:50%;" /> 

最后setText

3 View->Module

通过view变化的监听，取控件值给Module





---





##### **ViewModel**

保证数据的稳定性

###### **使用**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image177.png" alt="img" style="zoom:60%;" /> 

横竖屏切换也不会丢失数据



###### **原理**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image178.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image179.png" alt="img" style="zoom:80%;" /> 



1 Viewmodel生命周期（activity销毁的时候，vm才会被清空）

一直到destrory之前都是存活状态

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image180.png" alt="img" style="zoom:60%;" /> 

2 问题1中，横竖屏切换生命周期onDestroy，为什么vm没有被清除

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image181.png" alt="img" style="zoom:80%;" /> 









---







##### Navigation

Fragment生命周期研究下

###### **使用**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image182.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image183.png" alt="img" style="zoom:60%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image184.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image185.png" alt="img" style="zoom:80%;" /> 







###### **原理**

1.<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image186.png" alt="img" style="zoom:80%;" />

NavHostFragment 粘到Activity上

Activity的setContentView中，会查看布局中是否有FragmentContainerView

，并解析navGraph导航图，然后执行fragment的create方法，如下：

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image187.png" alt="img" style="zoom:80%;" /> 

默认启动第一个

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image188.png" alt="img" style="zoom:80%;" /> 



2跳转  replace

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image189.png" alt="img" style="zoom:80%;" /> 



因为是replace，相当于add3，remove2，所以2的生命周期会执行destroyview



---



##### WorkManager

退出后台或者重启后仍在执行  后台定时任务 比如小米面试题

疑问：可否保活

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image190.png" alt="img" style="zoom:80%;" /> 





---





#####  实践

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image191.png" alt="img" style="zoom:40%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image192.png" alt="img" style="zoom:50%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image193.png" alt="img" style="zoom:50%;" /> 

注意点：

通知 RemoteViews

粘性剔除和非剔除（登录后传数据到首页）

---



#### 架构

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image194.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image195.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image196.png" alt="img" style="zoom:80%;" /> 





---











------





### 三方库源码

#### OkHttp

##### **osi模型**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220830205414637.png" alt="image-20220830205414637" style="zoom: 50%;" />

前三个可以合并到应用层

##### **三次握手，四次挥手**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220830210251051.png" alt="image-20220830210251051" style="zoom: 50%;" />

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220830210312479.png" alt="image-20220830210312479" style="zoom: 50%;" />



##### **请求流程**

```
    OkHttpClient httpClient = new OkHttpClient();
    String url = "https://www.baidu.com/";
    Request getRequest = new Request.Builder().url(url).get().build();
    Call call = httpClient.newCall(getRequest);
    call.enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
        }
    });        
```

1.创建OkHttpClient、request实例，builder模式

2.异步请求：

```
void enqueue(AsyncCall call) {
    synchronized (this) {
      readyAsyncCalls.add(call); //存入等待执行的队列
    }
    promoteAndExecute(); //两处调用：添加异步请求时、同步/异步请求 结束时
  } 
    //调度的核心方法：在 控制异步并发 的策略基础上，使用线程池 执行异步请求
  private boolean promoteAndExecute() {
    List<AsyncCall> executableCalls = new ArrayList<>();
    boolean isRunning;
    synchronized (this) {
      for (Iterator<AsyncCall> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
        AsyncCall asyncCall = i.next();
        if (runningAsyncCalls.size() >= maxRequests) break; //最大并发数64
        if (asyncCall.callsPerHost().get() >= maxRequestsPerHost) continue; //Host最大并发数5
        i.remove();//从等待队列中移除
        asyncCall.callsPerHost().incrementAndGet();//Host并发数+1
        executableCalls.add(asyncCall);//加入 可执行请求 的集合
        runningAsyncCalls.add(asyncCall);//加入 正在执行的异步请求队列
      }
      isRunning = runningCallsCount() > 0;//正在执行的异步/同步 请求数 >0
    }
    for (int i = 0, size = executableCalls.size(); i < size; i++) {
      AsyncCall asyncCall = executableCalls.get(i);
      asyncCall.executeOn(executorService());//线程池中执行请求 见下面
    }
    return isRunning;
  }
	protected void execute() {
      try {
        Response response = getResponseWithInterceptorChain();执行请求获取结果
        responseCallback.onResponse(RealCall.this, response);//回调结果
      } catch (IOException e) {
      } finally {
        client.dispatcher().finished(this);//请求结束 掉用promoteAndExecute继续遍历等待队列，执行可执行队列
      }
    }
```

**总结如下：**

1：OkHttpClient okHttpClient = new OkHttpClient.Builder()
构建一个okhttpClient对象，传入你想传入的对象，不传就是默认的；
2：构建request对象
Request request = new Request.Builder()  
3：okHttpClient.newCall  实际上返回的realCall类 继续调用RealCall.newRealCall
4：调用enqueue方法，传入我们需要的回调接口，而且会判断，
synchronized (this) {
if (executed) throw new IllegalStateException("Already Executed");
executed = true;
}
如果当前这个call对象已经被运行的话，则抛出异常；
5：继续调用dispatcher的enqueue方法，如果当前运行队列<64并且正在运行，访问同一个服务器地址的请求<5，就直接添加到运行队列，并且开始运行；不然就添加到等待队列；线程池创建（相当于缓存线程池），核心为0，不占用资源

```
 executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
        new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Dispatcher", false));
        
public static ThreadFactory threadFactory(final String name, final boolean daemon) {
    return new ThreadFactory() {
      @Override public Thread newThread(Runnable runnable) {
        Thread result = new Thread(runnable, name);
        result.setDaemon(daemon);
        return result;
      }
    };
  }
```

> 核心线程数 保持在线程池中的线程数量
> 线程池最大可容纳的线程数  
> 当线程池中的线程数量大于核心线程数，空闲线程就会等待60s才会被终止，如果小于就会立刻停止；
> SynchronousQueue看下面面试题

6：运行AsyncCall，调用它的execute方法
7：在execute方法中处理完response之后，会在finally中调用dispathcer的finished方法；
8：当当前已经处理完毕的call从运行队列中移除掉；并且调用promoteCalls方法
9：promoteCalls方法中进行判断，如果运行队列数目大于等于64，如果等待队列里啥都没有，也直接return？
循环等待队列，将等待队列中的数据进行移除，移除是根据运行队列中还能存放多少来决定；移到了运行队列中，并且开始运行；

##### **拦截器：责任链模式**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image198.png" alt="img" style="zoom:80%;" /> 

```
Response getResponseWithInterceptorChain() throws IOException {
  List<Interceptor> interceptors = new ArrayList<>();
  interceptors.addAll(client.interceptors());
  interceptors.add(retryAndFollowUpInterceptor);
  interceptors.add(new BridgeInterceptor(client.cookieJar()));
  interceptors.add(new CacheInterceptor(client.internalCache()));
  interceptors.add(new ConnectInterceptor(client));
  if (!forWebSocket) {
    interceptors.addAll(client.networkInterceptors());
  }
  interceptors.add(new CallServerInterceptor(forWebSocket));
  Interceptor.Chain chain = new RealInterceptorChain(
      interceptors, null, null, null, 0, originalRequest);
  return chain.proceed(originalRequest);
}
```

**RetryAndFollowInterceptor**（重试和重定向  2xx  3xx）

重试：router、io异常、协议、证书等问题-不重试

更多路线 [www.baidu.com](https://www.baidu.com) 对应两个ip，可以重试

重定向：取出location，封装request去请求<20次

**BridgeInterceptor**（桥接）

添加header 压缩 解压

**CacheInterceptor**

只针对get，缓存

**ConnectInterceptor**☆☆☆☆☆

内部维护连接池，负责连接复用。ConnectionPool存放socket连接（ip/port）,默认5个存活5分钟，连接服务器先从连接池中找，没有则新建。

```
StreamAllocation streamAllocation = realChain.streamAllocation();//重试拦截器中创建 直接取出
//findHealthyConnection-findConnection 连接池中查找，找不到则新建并addconnections（之前会clean remove掉无用connection）
HttpCodec httpCodec = streamAllocation.newStream(client, doExtensiveHealthChecks);
RealConnection connection = streamAllocation.connection();
```

**CallServerInterceptor**

连接服务器发起请求

参考：https://www.jianshu.com/p/32de43ce0252

面试题：

**如果想修改response的cache-control，那么自定义的拦截器用哪种？**

> 答：addNetworkInterceptor,在response回到cacheInterceptor处理之前吧header修改好，如果用addInterceptor，那没有任何效果，因为此处response已经经过CacheInterceptor了，无力回天。

**自定义log拦截器放在两个地方区别**

> 答：addNetworkInterceptor，处理完的request，真正的请求；
>
> addInterceptor，相应区别不大，相应更全。

**为什么建立连接协议是三次握手，而关闭连接却是四次握手呢？**

> 这是因为服务端的LISTEN状态下的SOCKET当收到SYN报文的连接请求后，它可以把ACK和SYN(ACK起应答作用，而SYN起同步作用)放在一个报文里来发送。但关闭连接时，当收到对方的FIN报文通知时，它仅仅表示对方没有数据发送给你了；但未必你所有的数据都全部发送给对方了，所以你可能未必会马上会关闭SOCKET,也即你可能还需要发送一些数据给对方之后，再发送FIN报文给对方来表示你同意现在可以关闭连接了，所以它这里的ACK报文和FIN报文多数情况下都是分开发送的。

**http版本区别**

> http1.0:一次请求 会建立一个TCP连接，请求完成后主动断开连接。这种方法的好处是简单，各个请求互不干扰。
> 但每次请求都会经历 3次握手、2次或4次挥手的连接建立和断开过程——极大影响网络效率和系统开销。
>
> http1.1:**keep-alive机制**：一次HTTP请求结束后不会立即断开TCP连接，如果此时有新的HTTP请求，且其请求的Host同上次请求相同，那么会直接复用TCP连接。**连接的复用是串行的**
>
> http2.0:1.1中如果想并行请求，会建立多个tcp链接，增大网络开销。 **多路复用机制 就实现了 在同一个TCP连接上 多个请求 并行执行。**

**选择网络访问框架的时候，为什么要选择OkHttp而不是其他框架；**

> 明确一点：并不期待，你将市面上所有的框架都全部搞得非常清楚，优缺点全部列出来；你是否具备掌控网络访问框架的能力；
> 这个问题没有标准答案，最好是带点主观意识；
> OkHttp
> XUtil      支持网络请求，图片加载，甚至还能操作数据库；就我个人而言，我认为，一个好的网络访问框架应该只专注一件事
> Retrofit  肯定这个框架不错，它封装了OkHttp，所以我暂时没有去深入了解它，
> Volley  官方出品，官方介绍适合小中型app，接口比较多，访问量比较大；基于HttpUrlConnection封装，（HttpUrl。。。 android 2.3以下api）
> 就我个人而言，我更希望能够更加深入的去了解网络访问框架
>
> OkHttp基于Socket通信，它更倾向于底层，会对Http协议进行完全的封装，我在学习这个框架的时候，可以更加底层的了解；我相信只要我能搞定okhttp，那么其他的
> 访问框架，都很容易懂；

**OkHttp中为什么使用构建者模式？**

> 使用多个简单的对象一步一步构建成一个复杂的对象；
> 优点：当内部数据过于复杂的时候，可以非常方便的构建出我们想要的对象，并且不是所有的参数我们都需要进行传递；
> 缺点：代码会有冗余

**OkHttp线程池为什么使用SynchronousQueue？**

> **SynchronousQueue**，使用此队列意味着希望获得最大并发量。因为无论如何，向线程池提交任务，往队列提交任务都会失败。而失败后如果没有空闲的非核心线程，就会检查如果当前线程池中的线程数未达到最大线程，则会新建线程执行新提交的任务。完全没有任何等待，唯一制约它的就是最大线程数的个数。因此一般配合Integer.MAX_VALUE就实现了真正的无等待。
>
> **ArrayBlockingQueue**，首先加入到等待队列中，当等待队列满了之后，再次提交任务，尝试加入队列就会失败，这时就会检查如果当前线程池中的线程数未达到最大线程，则会新建线程执行新提交的任务。所以最终可能出现后提交的任务先执行，而先提交的任务一直在等待。
>
> **LinkedBlockingQueue**，当指定大小后，行为就和ArrayBlockingQueu一致。而如果未指定大小，则会使用默认的Integer.MAX_VALUE作为队列大小。这时候就会出现线程池的最大线程数参数无用，因为无论如何，向线程池提交任务加入等待队列都会成功。最终意味着所有任务都是在核心线程执行。如果核心线程一直被占，那就一直等待。
>
> OkHttp线程中当设置最大线程数为时，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性。但是OkHttp肯定也考虑到这方面，所以OkHttp设置了最大请求任务执行个数64个，有了这个限制。这样即解决了这个问题同时也能获得最大并发量。

**怎么设计一个自己的网络访问框架，为什么这么设计？**

> 我目前还没有正式设计过网络访问框架，
> 如果是我自己设计的话，我会从以下两个方面考虑
> 1：先参考现有的框架，找一个比较合适的框架作为启动点，比如说，基于上面讲到的okhttp的优点，选择okhttp的源码进行阅读，并且将主线的流程抽取出，为什么这么做，因为okhttp里面虽然涉及到了很多的内容，但是我们用到的内容并不是特别多；保证先能运行起来一个基本的框架；
> 2：考虑拓展，有了基本框架之后，我会按照我目前在项目中遇到的一些需求或者网路方面的问题，看看能不能基于我这个框架进行优化，比如服务器它设置的缓存策略，
> 我应该如何去编写客户端的缓存策略去对应服务器的，还比如说，可能刚刚去建立基本的框架时，不会考虑HTTPS的问题，那么也会基于后来都要求https，进行拓展；
>
> 为什么要基于Okhttp，就是因为它是基于Socket，从我个人角度讲，如果能更底层的深入了解相关知识，这对我未来的技术有很大的帮助；

**为什么okhttp请求很快？有哪些优化**

> 基于socket，tcp多路复用，而httpurlconnection每次三次握手。最多有64个连接池connectpool，每次请求的connection从pool中取，keeplive，这样就可以复用，不用每次都握手



##### **okhttp的长连接**

看咪咕项目，大概流程如下：

1.建立链接；

2.发送身份信息；

3.服务端拿到身份信息和会话进行绑定；

4.服务端指定身份，取出对应会话，进行发送信息。

客户端

```
  public void onOpen(WebSocket webSocket, Response response) {
        this.webSocket = webSocket;
        // 发送身份信息到服务器
        String userId = "123456"; // 替换为实际的用户身份信息
        webSocket.send(userId);
    }
```

服务端

```
public void onMessage(WebSocket webSocket, String text) {
        String[] parts = text.split(":");
        if (parts.length == 2) {
            String userId = parts[0];
            String message = parts[1];
            // 将用户身份信息与 WebSocket 会话关联
            userSessions.put(userId, webSocket);
            // 可以根据业务逻辑，向特定用户发送消息
            WebSocket userWebSocket = userSessions.get(userId);
            if (userWebSocket != null) {
                userWebSocket.send("Server: Hi, User " + userId + "! I received your message: " + message);
            }
        }
```

**什么是 OkHttp 长连接？**

> 答案：OkHttp 长连接是指 OkHttp 库在与服务器建立 HTTP 连接时，可以保持连接的状态，并在多次请求和响应之间重用同一个连接，从而减少了连接的建立和关闭的开销。这种方式可以提高网络性能，减少延迟和资源消耗。

**OkHttp 如何实现长连接？**

> 答案：OkHttp 实现长连接的方式是通过 HTTP/1.1 协议中的 "Connection: keep-alive" 头部字段来指示服务器保持连接的状态。OkHttp 在发送请求时，默认会添加 "Connection: keep-alive" 头部字段，从而告诉服务器保持连接。服务器在响应中也可以通过 "Connection: keep-alive" 头部字段来告诉 OkHttp 是否保持连接。

**OkHttp 如何处理长连接的超时？**

> 答案：OkHttp 默认使用连接池来管理长连接，并在连接池中维护了一个空闲连接的列表。当需要发送新的请求时，OkHttp 会首先尝试从连接池中获取一个可用的连接。如果连接池中没有可用的连接，或者连接池中的连接超过了设置的最大空闲连接数，OkHttp 会创建新的连接。OkHttp 还提供了连接超时和读写超时的设置，可以通过 OkHttpClient 的 connectTimeout() 和 readTimeout() 方法来设置连接超时和读写超时的时间。当连接超时或读写超时时，OkHttp 会自动关闭连接，并从连接池中移除该连接。

**OkHttp 长连接是否适用于所有的网络请求？**

> 答案：不适用。长连接适用于对同一服务器频繁进行多次请求和响应的场景，例如在 RESTful API 中进行多次请求和响应，或者在 WebSocket 通信中保持长连接。对于只进行一次请求和响应的场景，使用长连接反而可能增加了连接的维护和资源消耗。因此，在使用 OkHttp 时，需要根据具体的业务场景和需求来决定是否使用长连接。





------



#### Retrofit

构建者模式：4-5参数以上，并且可选



<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image199.png" alt="img" style="zoom:80%;" /> 



<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image200.png" alt="img" style="zoom:80%;" /> 



**CallAdapter是如何解析泛型的**



------



#### Rxjava

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image201.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image202.png" alt="img" style="zoom:80%;" /> 





subscribeOn(Sch.IO)

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image203.png" alt="img" style="zoom:80%;" /> 

observieOn(Sch.MAIN)

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image204.png" alt="img" style="zoom:80%;" /> 





补充：背压

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image205.png" alt="img" style="zoom:50%;" /> 





---



#### **Glide**

**(不看码牛，享学二期最新glide4.11主流程分析)**

```
RequestManager with = Glide.with(this);

RequestBuilder<Drawable> load = with.load(url);

load.into(iv);
```

![image-20220904140425347](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220904140425347.png)

![image-20220904142605830](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220904142605830.png)

![image-20220905214833510](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20220905214833510.png)

围绕时序图





磁盘缓存几种策略：

> DiskCacheStrategy.NONE： 表示不缓存任何内容。
>
> DiskCacheStrategy.RESOURCE： 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
>
> DiskCacheStrategy.DATA： 在资源解码前将原始数据写入磁盘缓存。
>
> DiskCacheStrategy.ALL ： 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
>
> DiskCacheStrategy.AUTOMATIC：它会尝试对本地和远程图片使用最佳的策略，平衡磁盘空间和获取图片成本。当你加载远程数据时，AUTOMATIC 策略仅会存储未被你的加载过程修改过的原始数据，因为这样相比缓存转换后的图占用磁盘空间更少。对于本地数据，AUTOMATIC 策略则会仅存储变换过的缩略图，因为即使你需要再次生成另一个尺寸或类型的图片，取回原始数据也很容易。默认使用这种缓存策略



Glide跟其他框架相比优势在哪里？

1：生命周期得管理

2：支持gif  picasso支持gif

3：三级缓存，内存缓存中还分为活动缓存和内存缓存；活动缓存指得是讲正在使用得图片用弱引用缓存，使用完成后到内存缓存；再到磁盘缓存；

4：占用内存小，它默认得编码格式是rgb565；  picasso用得argb8888 ImageLoader不支持gif图片加载 而且也很老了



如何设计自己的图片加载框架





**Glide如果在子线程加载，会有啥问题？**

> 不会创建空白fragment，主线程才会去新建空白fragment监听生命周期，into会判断主线程
>
> ```
> public ViewTarget<ImageView, TranscodeType> into(@NonNull ImageView view) {
>     Util.assertMainThread();
> ```

**有内存缓存了为什么还要设计活动缓存**

> 如果只有内存缓存Lru，正在使用的图片很有可能被删除掉，会引起崩溃。

**三级缓存之间是如何进行互动的？**

> Lru和活动缓存里只有一份，一张图不会同时存在两个里面，只会移来移去，正在使用会放入活动，如果不用了（关闭act）则会移动到Lru中

**Glide设置rgb565真的有效吗？**

> 不一定，对于有透明通道的argb图片，仍然是argb8888的格式，而不是565，argb8888是32位（bit）,换算4字节，565图片是16位，换算2字节，内存占用少一半 。当设置图片格式为RGB_565的时候，并不是所有图片都会按照这个格式进行输出。在Glide内部，会读取原始图片的EXIF头信息，获取当前图片的格式。若当前格式的图片支持alpha通道，则还是会设置为ARGB_8888的格式。

**placeholder error fallback区别，以及设置？**

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image206.png" alt="img" style="zoom:50%;" /> 

**如何保障一个fragment**

> 1 从集合中取   2 队列无需等待 马上工作（handler 不好理解）

**为什么使用glide，还不是其他几个库**

> fresco适用于海外低端机，放在匿名共享内存中，不用堆内存回收，更多内存加载应用程序

**如何切换其他框架？ImageLoader 如何扩展以及切换图片请求框架**

> 本框架默认使用 `Glide` 实现图片加载功能, 使用 **ImageLoader** 为业务层提供统一的图片请求接口, **ImageLoader** 使用策略模式和建造者模式, 可以动态切换图片请求框架 (比如说切换成 `Picasso`), 并且加载图片时传入的参数也可以随意扩展 ( **loadImage()** 方法在需要扩展参数时, 调用端也不需要改动, 全部通过 **Builder** 扩展, 比如您想让内部的图片加载框架, 清除缓存您只需要定义个 **boolean** 字段, 内部的图片加载框架根据这个字段 **if|else** 做不同的操作, 其他操作同理, 当需要切换图片请求框架或图片请求框架升级后变更了 **Api** 时, 这里可以将影响范围降到最低, 所以封装 **ImageLoader** 是为了屏蔽这个风险)

- 本框架默认提供了 **GlideImageLoaderStrategy** 和 **ImageConfigImpl** 简单实现了图片加载逻辑 (v2.5.0 版本后, 需要依赖 **arms-imageloader-glide** 扩展库, 并自行通过 GlobalConfigModule.Builder#imageLoaderStrategy(new GlideImageLoaderStrategy); 完成注册), 方便快速使用, 但开发中难免会遇到复杂的使用场景, 所以本框架推荐即使不切换图片请求框架继续使用 **Glide**, 也请按照下面的方法, 自行实现图片加载策略, 因为默认实现的 **GlideImageLoaderStrategy** 是直接打包进框架的, 如果是远程依赖, 当遇到满足不了需求的情况, 您将不能扩展里面的逻辑
- 使用 **ImageLoader** 必须传入一个实现了 **BaseImageLoaderStrategy** 接口的图片加载实现类从而实现动态切换, 所以首先要实现**BaseImageLoaderStrategy**, 实现时必须指定一个继承自 **ImageConfig** 的实现类, 使用建造者模式, 可以储存一些信息, 比如 **URL**、**ImageView**、**Placeholder** 等, 可以不断的扩展, 供图片加载框架使用

```
public class PicassoImageLoaderStrategy implements BaseImageLoaderStrategy<PicassoImageConfig> {
	 @Override
    public void loadImage(Context ctx, PicassoImageConfig config) {
                        Picasso.with(ctx)
    			.load(config.getUrl())
    			.into(config.getImageView());
    ｝
}
```

- 实现 **ImageConfig**, 使用建造者模式 (创建新的 **PicassoImageConfig** 适用于新项目, 如果想重构之前的项目, 使用其他图片加载框架, 为了避免影响之前的代码, 请继续使用默认提供的 **ImageConfigImpl** 或者您之前自行实现的 **ImageConfig**, 继续扩展里面的属性)

```
public class PicassoImageConfig extends ImageConfig {
    private PicassoImageConfig(Buidler builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.errorPic = builder.errorPic;
    }
    public static Buidler builder() {
        return new Buidler();
    }
    public static final class Buidler {
        private String url;
        private ImageView imageView;
        private int placeholder;
        protected int errorPic;
        private Buidler() {
        }
        public Buidler url(String url) {
            this.url = url;
            return this;
        }
        public Buidler placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }
        public Buidler errorPic(int errorPic){
            this.errorPic = errorPic;
            return this;
        }
        public Buidler imagerView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }
        public PicassoImageConfig build() {
            if (url == null) throw new IllegalStateException("url is required");
            if (imageView == null) throw new IllegalStateException("imageview is required");
            return new PicassoImageConfig(this);
        }
    }
}
```

- 在 **App** 刚刚启动初始化时, 通过 [**GlobalConfigModule**](https://github.com/JessYanCoding/MVPArms/wiki#3.1) 传入上面扩展的 **PicassoImageLoaderStrategy**, 也可以在 **App** 运行期间通过 **AppComponent** 拿到 **ImageLoader** 对象后, 调用 **setLoadImgStrategy(new PicassoImageLoaderStrategy)** 替换之前的实现 (默认使用 `Glide`)

```
方法一: 通过GlobalConfigModule传入
public class GlobalConfiguration implements ConfigModule {
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        builder.imageLoaderStrategy(new PicassoImageLoaderStrategy);
    }
}
方法二: 拿到 AppComponent 中的 ImageLoader, 通过方法传入
ArmsUtils.obtainAppComponentFromContext(context)
	.imageLoader()
	.setLoadImgStrategy(new PicassoImageLoaderStrategy());
使用方法:
ArmsUtils.obtainAppComponentFromContext(context)
	.imageLoader()
	.loadImage(mApplication, PicassoImageConfig
                .builder()
                .url(data.getAvatarUrl())
                .imagerView(mAvater)
                .build());
```



---

#### Arouter

参考：https://www.modb.pro/db/211855（很详细）

**原理分析：**

> 1.apt编译时**com.alibaba.android.arouter.routes.ARouter**开头的类
>
> 2.运行时-注入
>
> - 1 在Application的onCreate()里面我们调用了Arouter.init(this)。
> - 2 接着调用了ClassUtils.getFileNameByPackageNam()来获取所有"com.alibaba.android.arouter.routes"目录下的dex文件的路径。
> - 3 然后遍历这些dex文件获取所有的calss文件的完整类名。
> - 4 然后遍历所有类名，获取指定前缀的类，然后通过反射调用它们的loadInto(map)方法，这是个注入的过程，都注入到参数Warehouse的成员变量里面了。
> - 5 其中就有Arouter在编译时生成的"com.alibaba.android.arouter.routes.ARouter$$Root.ARouter\$$Root$$app"类，它对应的代码:<"app", ARouter\$$Group$$app.class>就被添加到Warehouse.groupsIndex里面了
>
> 3.运行时-获取
>
> ARouter.getInstance().build(path);最终是创建了个Postcard，保存了path和group
>
>

**如何提升arouter的启动速度**

> 字节码插装，arouter已经做好了aop的插件，引入对应插件即可，原理是ASM进行字节码插装transform





疑问1：为什么init时需要遍历整个apk?

> 因为arouter不知道哪些文件下是跟arouter路由表相关的，所以先找到对应的class文件然后反射loadinto

疑问2：为什么字节码插桩省时间了？

> 省去了遍历整个apk的过程，指向性的找到对应的模块名，然后反射loadinto



阉割版：咱项目中的用法

1.APT注解处理器在编译时期生成路由表RouterApp$modulename

```
public final class RouterApp$article_module extends RouterClass {
  @Override
  public void init() {
    map.put("article/ArticleDetailActivity",ArticleDetailActivity.class);
  }
}
```

2.在build中手动配置各个modulename[]

3.application中create进行init，将生成的路由表加载进内存map集合，方便后面读取

```
public void init(String[] modules) {
        for (String module : modules) {
            try {
                Class cls = Class.forName(packageName + ".RouterApp$" + module);
                RouterClass path = (RouterClass) cls.newInstance();
                path.init();
                routerClasses.add(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
```

3.跳转时根据key取出对应class文件进行跳转

```
ActivityUtil.getInstance().startActivity(context, "article/ArticleDetailActivity");
```

```
public void startActivity(Context context,String routerPath, Intent intent){
		Class cls = Router.getInstance().getClass(routerPath);
		if (!(context instanceof Activity)) {
  	intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
	}
	intent.setClass(context, cls);
	context.startActivity(intent);
}
```







#### EventBus

**创建**

双重检索单例创建EventBus实例

```
public static EventBus getDefault() {
    EventBus instance = defaultInstance;
    if (instance == null) {
        synchronized (EventBus.class) {
            instance = EventBus.defaultInstance;
            if (instance == null) {
                instance = EventBus.defaultInstance = new EventBus();
            }
        }
    }
    return instance;
}
```

**注册**

```
EventBus.getDefault().register(this);
public void register(Object subscriber) {
    Class<?> subscriberClass = subscriber.getClass();
    List<SubscriberMethod> subscriberMethods = subscriberMethodFinder.findSubscriberMethods(subscriberClass);
    synchronized (this) {
        for (SubscriberMethod subscriberMethod : subscriberMethods) {
            subscribe(subscriber, subscriberMethod);
        }
    }
}
List<SubscriberMethod> findSubscriberMethods(Class<?> subscriberClass) {
				if (ignoreGeneratedIndex) {
            subscriberMethods = findUsingReflection(subscriberClass); //反射
        } else {
            subscriberMethods = findUsingInfo(subscriberClass);
}

private void subscribe(Object subscriber, SubscriberMethod subscriberMethod) {
    Class<?> eventType = subscriberMethod.eventType;
    Subscription newSubscription = new Subscription(subscriber, subscriberMethod);
    CopyOnWriteArrayList<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
    if (subscriptions == null) {
        subscriptions = new CopyOnWriteArrayList<>();
        subscriptionsByEventType.put(eventType, subscriptions);
    } else {
        if (subscriptions.contains(newSubscription)) {
            throw new EventBusException("Subscriber " + subscriber.getClass() + " already registered to event " + eventType);
        }
    }
    int size = subscriptions.size();
    for (int i = 0; i <= size; i++) {
        if (i == size || subscriberMethod.priority > subscriptions.get(i).subscriberMethod.priority) {
            subscriptions.add(i, newSubscription);
            break;
        }
    }
    List<Class<?>> subscribedEvents = typesBySubscriber.get(subscriber);
    if (subscribedEvents == null) {
        subscribedEvents = new ArrayList<>();
        typesBySubscriber.put(subscriber, subscribedEvents);
    }
    subscribedEvents.add(eventType);
    //......粘性事件处理
}
```

首先获取订阅对象的`Class`,然后通过`SubscriberMethodFinder`类查找所有的订阅方法并封装在`SubscriberMethod`中,`SubscriberMethod`包含订阅方法的关键信息，比如线程模型,优先级,参数,是否为黏性事件等,`findSubscriberMethods()`方法返回的是一个`List`,说明一个订阅者可以订阅多种事件。

**发送**

```
while (!eventQueue.isEmpty()) {
    postSingleEvent(eventQueue.remove(0), postingState);//发送即移除
}

private void postSingleEvent(Object event, PostingThreadState postingState) throws Error {   
        for (int h = 0; h < countTypes; h++) {
             Class<?> clazz = eventTypes.get(h);
             subscriptionFound |= postSingleEventForEventType(event, postingState, clazz);
         }
    }
private void postToSubscription(Subscription subscription, Object event, boolean isMainThread) {
        switch (subscription.subscriberMethod.threadMode) {
            case POSTING:
                invokeSubscriber(subscription, event);//反射
                break;
            case MAIN:
                if (isMainThread) {
                    invokeSubscriber(subscription, event);//反射
                } else {
                    mainThreadPoster.enqueue(subscription, event);
                }
                break;
                。。。
        }
    }
```

**粘性**



**优化**

> 针对register部分进行apt优化，编译过程中生成类添加每个类里面的接受方法，所有的类添加进一个集合







































---





#### Dagger2

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image207.png" alt="img" style="zoom:80%;" /> 





#### LeakCanary

LeakCanary怎么检测内存泄露（原理）

工作流程是啥

#### BlockCanary





### 黑科技

#### 热修复

##### **Robust**

即时生效，简单代码帮助理解：

![image-20230722194413749](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20230722194413749.png)

1、打基础包时插桩，在每个方法前插入一段类型为 ChangeQuickRedirect 静态变量的逻辑，插入过程对业务开发是完全透明

```
public static ChangeQuickRedirect u;
protected void onCreate(Bundle bundle) {
        if (u != null) {
            if (PatchProxy.isSupport(new Object[]{bundle}, this, u, false, 78)) {
                PatchProxy.accessDispatchVoid(new Object[]{bundle}, this, u, false, 78);
                return;
            }
        }
        super.onCreate(bundle);
        ...
    }
```

发生在Class字节码生成后(打包成dex之前，编译时之后)，也称为Class字节码手术刀

robust是如何在每个方法里加代码，自定义插件transform，class->dex期间



2、加载补丁时，从补丁包中读取要替换的类及具体替换的方法实现，新建ClassLoader加载补丁dex。当changeQuickRedirect不为null时，可能会执行到accessDispatch从而替换掉之前老的逻辑，达到fix的目的

修复代码如下：

```
public class PatchExecutor extends Thread {
    @Override
    public void run() {
        ...
        applyPatchList(patches);
        ...
    }
    /**
     * 应用补丁列表
     */
    protected void applyPatchList(List<Patch> patches) {
        ...
        for (Patch p : patches) {
            ...
            currentPatchResult = patch(context, p);
            ...
            }
    }
     /**
     * 核心修复源码
     */
    protected boolean patch(Context context, Patch patch) {
        ...
        //新建ClassLoader
        DexClassLoader classLoader = new DexClassLoader(patch.getTempPath(), context.getCacheDir().getAbsolutePath(),
                null, PatchExecutor.class.getClassLoader());
        patch.delete(patch.getTempPath());
        ...
        try {
            patchsInfoClass = classLoader.loadClass(patch.getPatchesInfoImplClassFullName());
            patchesInfo = (PatchesInfo) patchsInfoClass.newInstance();
            } catch (Throwable t) {
             ...
        }
        ...
        //通过遍历其中的类信息进而反射修改其中 ChangeQuickRedirect 对象的值
        for (PatchedClassInfo patchedClassInfo : patchedClasses) {
            ...
            try {
                oldClass = classLoader.loadClass(patchedClassName.trim());
                Field[] fields = oldClass.getDeclaredFields();
                for (Field field : fields) {
                    if (TextUtils.equals(field.getType().getCanonicalName(), ChangeQuickRedirect.class.getCanonicalName()) && TextUtils.equals(field.getDeclaringClass().getCanonicalName(), oldClass.getCanonicalName())) {
                        changeQuickRedirectField = field;
                        break;
                    }
                }
                ...
                try {
                    patchClass = classLoader.loadClass(patchClassName);
                    Object patchObject = patchClass.newInstance();
                    changeQuickRedirectField.setAccessible(true);
                    changeQuickRedirectField.set(null, patchObject);
                    } catch (Throwable t) {
                    ...
                }
            } catch (Throwable t) {
                 ...
            }
        }
        return true;
    }
}
```

**优点**

- 高兼容性（Robust只是在正常的使用DexClassLoader）、高稳定性，修复成功率高达99.9%
- 补丁实时生效，不需要重新启动
- 支持方法级别的修复，包括静态方法
- 支持增加方法和类
- 支持ProGuard的混淆、内联、优化等操作

**缺点**

- 代码是侵入式的，会在原有的类中加入相关代码
- so和资源的替换暂时不支持
- 会增大apk的体积，平均一个函数会比原来增加17.47个字节，10万个函数会增加1.67M



##### **QQ空间**

核心classloader

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image64.png" alt="img" style="zoom:80%;" /> 

<img src="https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/webp-20220929180349081" alt="img" style="zoom:50%;" />

不足：

1.不支持即时生效，必须通过重启才能生效。

2.为了实现修复这个过程，必须在应用中加入两个dex!dalvikhack.dex中只有一个类，对性能影响不大，但是对于patch.dex来说，修复的类到了一定数量，就需要花不少的时间加载。对手淘这种航母级应用来说，启动耗时增加2s以上是不能够接受的事。

3.在ART模式下，如果类修改了结构，就会出现内存错乱的问题。为了解决这个问题，就必须把所有相关的调用类、父类子类等等全部加载到patch.dex中，导致补丁包异常的大，进一步增加应用启动加载的时候，耗时更加严重。



##### 微信Tinker

高版本自定义classloader反射替换pathclassloader？

微信针对QQ空间超级补丁技术的不足提出了一个提供DEX差量包，整体替换DEX的方案。主要的原理是与QQ空间超级补丁技术基本相同，区别在于不再将patch.dex增加到elements数组中，而是差量的方式给出patch.dex，然后将patch.dex与应用的classes.dex合并，然后整体替换掉旧的DEX文件，以达到修复的目的

<img src="https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20220929180239831.png" alt="image-20220929180239831" style="zoom: 67%;" />

<img src="https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/webp" alt="img" style="zoom:50%;" />

不足：

1.与超级补丁技术一样，不支持即时生效，必须通过重启应用的方式才能生效。

2.需要给应用开启新的进程才能进行合并，并且很容易因为内存消耗等原因合并失败。

3.合并时占用额外磁盘空间，对于多DEX的应用来说，如果修改了多个DEX文件，就需要下发多个patch.dex与对应的classes.dex进行合并操作时这种情况会更严重，因此合并过程的失败率也会更高。





**几种热修复框架选型**

| 方案对比   | Sophix               | Tinker                       | nuwa | AndFix | Robust | Amigo |
| :--------- | :------------------- | :--------------------------- | :--- | :----- | :----- | :---- |
| 类替换     | yes                  | yes                          | yes  | no     | no     | yes   |
| So替换     | yes                  | yes                          | no   | no     | no     | yes   |
| 资源替换   | yes                  | yes                          | yes  | no     | no     | yes   |
| 全平台支持 | yes                  | yes                          | yes  | no     | yes    | yes   |
| 即时生效   | 同时支持             | no                           | no   | yes    | yes    | no    |
| 性能损耗   | 较少                 | 较小                         | 较大 | 较小   | 较小   | 较小  |
| 补丁包大小 | 小                   | 较小                         | 较大 | 一般   | 一般   | 较大  |
| 开发透明   | yes                  | yes                          | yes  | no     | no     | yes   |
| 复杂度     | 傻瓜式接入           | 复杂                         | 较低 | 复杂   | 复杂   | 较低  |
| Rom体积    | 较小                 | Dalvik较大                   | 较小 | 较小   | 较小   | 大    |
| 成功率     | 高                   | 较高                         | 较高 | 一般   | 最高   | 较高  |
| 热度       | 高                   | 高                           | 低   | 低     | 高     | 低    |
| 开源       | no                   | yes                          | yes  | yes    | yes    | yes   |
| 收费       | 收费（设有免费阈值） | 收费（基础版免费，但有限制） | 免费 | 免费   | 免费   | 免费  |
| 监控       | 提供分发控制及监控   | 提供分发控制及监控           | no   | no     | no     | no    |

**即时生效**是我们的一个优先选择点，用户可能崩溃过一次，就不会打开第二次了，这期间会损失不少用户，所以这一点很重要。

**成功率**用户量比较多，机型也很杂，所以成功率高会优先选择

**免费！！！并且头条都在用**

以上三点基本上就定位了robust，虽然它不支持类替换、资源替换等功能，但是我们一般都是为了紧急处理线上崩溃才去使用热修复，基本上都是一些空指针、数据越界引起的，所以针对相关类去修复已经够用了，功能过于庞大反而失去了热修复的意义了，有点变相的迭代版本功能需求的味道。







#### 插件化

##### 基础

DroidPlugin 虽然不咋用，但是帮助理解原理

![image-20230714101021592](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230714101021592.png)

**插件化解决的问题**

> 1.APP的功能模块越来越多，体积越来越大
> 2.模块之间的耦合度高，协同开发沟通成本越来越大
> 3.方法数日可能超过65535，APP占用的内存过大
> 4.应用之间的互相调用

**插件化与组件化的区别**

> 组件化开发就是将一个app分成多个模块，每个模块都是一个组件，开发的过程中我们可以让这些组件相互依赖或者单独调试部分组件等，但是最终发布的时候是将这些组件合并统一成一个apk,这就是组件化开发。
> 插件化开发和组件化略有不同。
>
> 插件化开发是将整个app拆分成多个模块，这些模块包括一个宿主和多个插件，每个模块都是一个apk,最终打包的时候宿主apk和插件apk分开打包。
>
> 前者参与打包，后者不参与；前者体积大，后者体积小；前者有bug的话组件不能动态改，后者插件有bug，可以动态修改下发。那为什么不用插件化代替热修复？（大材小用了，本来就要修复一个类里面代码，但是插件化换了整个）





##### **插件化实现思路分三步**

**第一步：如何加载插件的类？**
Classloader看一遍先

![image-20230714111136680](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230714111136680.png)

思路有了，接下来就是玩转反射！

```
 public class LoadUtil {
        private final static String apkPath = "/sdcard/plugin-debug.apk";
        public static void loadClass(Context context) {
            /**
             * 宿主dexElements = 宿主dexElements + 插件dexElements
             * 1.获取宿主dexElements
             * 2.获取插件dexElements
             * 3.合并两个dexElements
             * 4.将新的dexElements 赋值到 宿主dexElements
             *
             * 目标：dexElements  -- DexPathList类的对象 -- BaseDexClassLoader的对象，类加载器
             *
             * 获取的是宿主的类加载器  --- 反射 dexElements  宿主
             *
             * 获取的是插件的类加载器  --- 反射 dexElements  插件
             */
            try {
                Class<?> clazz = Class.forName("dalvik.system.BaseDexClassLoader");
                Field pathListField = clazz.getDeclaredField("pathList");
                pathListField.setAccessible(true);

                Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
                Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
                dexElementsField.setAccessible(true);

                // 宿主的 类加载器
                ClassLoader pathClassLoader = context.getClassLoader();
                // DexPathList类的对象
                Object hostPathList = pathListField.get(pathClassLoader);
                // 宿主的 dexElements
                Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);

                // 插件的 类加载器
                ClassLoader dexClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(),
                        null, pathClassLoader);
                // DexPathList类的对象
                Object pluginPathList = pathListField.get(dexClassLoader);
                // 插件的 dexElements
                Object[] pluginDexElements = (Object[]) dexElementsField.get(pluginPathList);

                // 宿主dexElements = 宿主dexElements + 插件dexElements
								// Object[] obj = new Object[]; // 不行
                // 创建一个新数组
                Object[] newDexElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(),
                        hostDexElements.length + pluginDexElements.length);

                System.arraycopy(hostDexElements, 0, newDexElements,
                        0, hostDexElements.length);
                System.arraycopy(pluginDexElements, 0, newDexElements,
                        hostDexElements.length, pluginDexElements.length);
                // 赋值
                // hostDexElements = newDexElements
                dexElementsField.set(hostPathList, newDexElements);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
```

**第二步：如何启动插件的四大组件？**

![image-20230714153443300](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230714153443300.png)

启动Activity但是没有注册，如何绕过AMS检查，使用ProxyActivity

![image-20230714140316736](https://raw.githubusercontent.com/AndroidJiang/img2/master/images2/image-20230714140316736.png)

```
try {
    intent.migrateExtraStreamToClipData(who);
    intent.prepareToLeaveProcess(who);
    int result = ActivityTaskManager.getService().startActivity(whoThread,
            who.getBasePackageName(), who.getAttributionTag(), intent,
            intent.resolveTypeIfNeeded(who.getContentResolver()), token,
            target != null ? target.mEmbeddedID : null, requestCode, 0, null, options);
    checkStartActivityResult(result, intent);
} catch (RemoteException e) {
    throw new RuntimeException("Failure from system", e);
}
```

```
//从AMS跨进程启动act为hook点，修改intent为代理act（动态代理）
public class HookUtil {
    private static final String TARGET_INTENT = "target_intent";
    public static void hookAMS() {
        try {
            // 获取 singleton 对象
            Field singletonField = null;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) { // 小于8.0
                Class<?> clazz = Class.forName("android.app.ActivityManagerNative");
                singletonField = clazz.getDeclaredField("gDefault");
            } else {
                Class<?> clazz = Class.forName("android.app.ActivityManager");
                singletonField = clazz.getDeclaredField("IActivityManagerSingleton");
            }

            singletonField.setAccessible(true);
            Object singleton = singletonField.get(null);

            // 获取 系统的 IActivityManager 对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            final Object mInstance = mInstanceField.get(singleton);
            Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");
            // 创建动态代理对象
            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManagerClass}, new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            // Intent的修改 -- 过滤
                            // 过滤
                            if ("startActivity".equals(method.getName())) {
                                int index = -1;

                                for (int i = 0; i < args.length; i++) {
                                    if (args[i] instanceof Intent) {
                                        index = i;
                                        break;
                                    }
                                }
                                // 启动插件的
                                Intent intent = (Intent) args[index];
                                Intent proxyIntent = new Intent();
                                proxyIntent.setClassName("com.enjoy.leo_plugin",
                                        "com.enjoy.leo_plugin.ProxyActivity");
                                proxyIntent.putExtra(TARGET_INTENT, intent);
                                args[index] = proxyIntent;
                            }
                            // args  method需要的参数  --- 不改变原有的执行流程
                            return method.invoke(mInstance, args);
                        }
                    });
            // ActivityManager.getService() 替换成 proxyInstance
            mInstanceField.set(singleton, proxyInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//AMS执行代理act生命周期时修改为真实act（反射）
    public static void hookHandler() {
        try {
            // 获取 ActivityThread 类的 Class 对象
            Class<?> clazz = Class.forName("android.app.ActivityThread");
            // 获取 ActivityThread 对象
            Field activityThreadField = clazz.getDeclaredField("sCurrentActivityThread");
            activityThreadField.setAccessible(true);
            Object activityThread = activityThreadField.get(null);
            // 获取 mH 对象
            Field mHField = clazz.getDeclaredField("mH");
            mHField.setAccessible(true);
            final Handler mH = (Handler) mHField.get(activityThread);
            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);
            // 创建的 callback
            Handler.Callback callback = new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    // 通过msg  可以拿到 Intent，可以换回执行插件的Intent
                    // 找到 Intent的方便替换的地方  --- 在这个类里面 ActivityClientRecord --- Intent intent 非静态
                    // msg.obj == ActivityClientRecord
                    switch (msg.what) {
                        case 100:
                            try {
                                Field intentField = msg.obj.getClass().getDeclaredField("intent");
                                intentField.setAccessible(true);
                                // 启动代理Intent
                                Intent proxyIntent = (Intent) intentField.get(msg.obj);
                                // 启动插件的 Intent
                                Intent intent = proxyIntent.getParcelableExtra(TARGET_INTENT);
                                if (intent != null) {
                                    intentField.set(msg.obj, intent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 159:
                            try {
                                // 获取 mActivityCallbacks 对象
                                Field mActivityCallbacksField = msg.obj.getClass()
                                        .getDeclaredField("mActivityCallbacks");

                                mActivityCallbacksField.setAccessible(true);
                                List mActivityCallbacks = (List) mActivityCallbacksField.get(msg.obj);

                                for (int i = 0; i < mActivityCallbacks.size(); i++) {
                                    if (mActivityCallbacks.get(i).getClass().getName()
                                            .equals("android.app.servertransaction.LaunchActivityItem")) {
                                        Object launchActivityItem = mActivityCallbacks.get(i);

                                        // 获取启动代理的 Intent
                                        Field mIntentField = launchActivityItem.getClass()
                                                .getDeclaredField("mIntent");
                                        mIntentField.setAccessible(true);
                                        Intent proxyIntent = (Intent) mIntentField.get(launchActivityItem);

                                        // 目标 intent 替换 proxyIntent
                                        Intent intent = proxyIntent.getParcelableExtra(TARGET_INTENT);
                                        if (intent != null) {
                                            mIntentField.set(launchActivityItem, intent);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    // 必须 return false
                    return false;
                }
            };
            // 替换系统的 callBack
            mCallbackField.set(mH, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

**第三步：如何加载插件的资源？**

单独给插件创建一个 Resource

都是宿主的 context  --- 插件自己创建一个 context -- 绑定 启动插件资源的  Resource

```
public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_main, null);
        setContentView(view);
    }
}

//BaseActivity
@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resources = LoadUtil.getResources(getApplication());
        mContext = new ContextThemeWrapper(getBaseContext(), 0);
        Class<? extends Context> clazz = mContext.getClass();
        try {
            Field mResourcesField = clazz.getDeclaredField("mResources");
            mResourcesField.setAccessible(true);
            mResourcesField.set(mContext, resources);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //LoadUtil
      private static Resources loadResource(Context context) {
        // assets.addAssetPath(key.mResDir)
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            // 让 这个 AssetManager对象 加载的 资源为插件的
            Method addAssetPathMethod = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, apkPath);
            // 如果传入的是Activity的 context 会不断循环，导致崩溃
            Resources resources = context.getResources();
            // 加载插件的资源的 resources
            return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
```



参考360DroidPlugin(该开源项目已经停止维护 , 就适配到了 8.0 , 9.0 Android 系统无法运行)



##### **Shadow**(重点)

原理可以查看码牛b站

以上插件化方案需要反射+hook，需要做版本兼容，比较麻烦，所以shadow现在唯一的插件化方案

![image-20230722110546139](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/818/image-20230722110546139.png)

| module称号                   | module编译产品    | 终究产品方式      | 是否动态加载 | 代码运转所在进程     | 首要职责                                             |
| ---------------------------- | ----------------- | ----------------- | ------------ | -------------------- | ---------------------------------------------------- |
| sample-host                  | 可独立运转的apk   | 可独立运转的apk   | 否           | 主进程和插件进程均有 | 是对外发布的app                                      |
| sample-manager               | pluginmanager.apk | pluginmanager.apk | 是           | 主进程               | 装置、办理及加载插件                                 |
| sample-plugin/sample-app     | app-plugin.apk    | plugin.zip        | 是           | 插件进程             | 事务逻辑                                             |
| sample-plugin/sample-base    | base-plugin.apk   | plugin.zip        | 是           | 插件进程             | 事务逻辑，被app以compileOnly的办法依靠               |
| sample-plugin/sample-loader  | loader.apk        | plugin.zip        | 是           | 插件进程             | 插件的加载                                           |
| sample-plugin/sample-runtime | runtime.apk       | plugin.zip        | 是           | 插件进程             | 插件运转时的署理组件，如container activity（见下文） |

**资源 ID 抵触问题**

那么下一个问题，便是插件中必定也会有对资源的拜访。一般状况下，资源拜访会是相似下面的这样的方式：

```c
textView.setText(R.string.main_activity_info);
```

咱们对资源的拜访经过一个int值，而这个值是在apk的打包期间，由脚本生成的。这个值与详细的资源之间存在一一对应的关系。

由于插件和宿主工程是独立编译的，假如不修正分区，两者的资源或许存在抵触，这个时分就不知道应该去哪里加载资源了。

为了处理这个问题，Shadow修正了插件资源的id的分区。修正资源id并不杂乱，只需求一行代码就能够处理：

```arduino
additionalParameters "--package-id", "0x7E", "--allow-reserved-package-id"
```

反编译打包完结的apk，也很容易就能够发现，同一个资源的分区是不同的。宿主工程的是7f开头，而插件则是7e。



**Replugin的思路：**

Hack宿主的ClassLoader，使得体系收到加载ContainerActivity的恳求时，回来的是PluginActivity类。

由于PluginActivity本质上也是一个承继了android.app.Activity的类，经过向上转型为activity去运用，理论上不会存在什么问题。

Replugin的这个计划的问题之一，是需求在宿主apk中，为每一个插件的事务Activity注册一个对应的坑位Activity、。关于这点，咱们先看下ClassLoader load办法的签名：

```arduino
public abstract class ClassLoader {
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        ......
    }
}
```

能够看到，ClassLoader在loadClass的时分，收到的参数只要一个类名。这就导致，关于每个事务插件中的Activity，都需求一个ContainerActivity与之对应。在宿主apk中，咱们需求注册许多的坑位Activity。

别的，Replugin hack了加载class的进程，后边也不得不持续用Hack手段处理体系看到了未装置的Activity的问题。比如体系为插件Activity[初始化](https://www.6hu.cc/archives/tag/初始化)的Context是以宿主的apk初始化的，插件结构就不得不再去Hack修复。

**Shadow的思路**

Shadow则运用了另一种思路。已然对体系而言，ContainerActivity是一个实在注册过的存在的activity，那么就让这个activity发动起来。

一同，让ContainerActivity持有PluginActivity的实例。ContainerActivity将自己的各类办法，顺次转发给PluginActivity去完结，如onCreate等生命周期的办法。

Shadow在这儿所采用的计划，本质上是一种署理的思路。在这种思路中，事实上，PluginActivity并不需求实在承继Activity，它只需求承继一个与Activity有着相似的办法的接口就能够了。

Shadow的这个思路，一个ContainerActivity能够对应多个PluginActivity，咱们只需求在宿主中注册有限个有必要的activity即可。

而且，后续插件假如想要新增一个activity，也不是有必要要修正宿主工程。只要事务上答应，完全能够复用已有的ContainerActivity。



**Shadow是如何加载插件中的dex的**

```scss
new DexClassLoader(apkFile.getAbsolutePath(), oDexDir.getAbsolutePath(), null, ODexBloc.class.getClassLoader());
```

**Shadow是如何加载资源包的**

```
val packageManager = hostAppContext.packageManager
        packageArchiveInfo.applicationInfo.publicSourceDir = archiveFilePath
        packageArchiveInfo.applicationInfo.sourceDir = archiveFilePath
        packageArchiveInfo.applicationInfo.sharedLibraryFiles = hostAppContext.applicationInfo.sharedLibraryFiles
        try {
            return packageManager.getResourcesForApplication(packageArchiveInfo.applicationInfo)
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException(e)
        }
```

也是通过正常的packageManager生成的。但是是生成一个新的Resources，区别于宿主中正常使用的Resources。由于插件和宿主中是不通的Resources，所以不会出现资源ID冲突的问题。



参考文章

https://www.6hu.cc/archives/181080.html















#### AOP



##### APT

注解处理器，ButterKnife、Dagger、ARouter都是用的apt在编译期生成对应代码



##### **JavaSsist**









---



### 性能优化

LeakCanary

不做线上

####  启动优化

**首先知道如何去计算时间，然后提供解决方案**

**方案汇总：**

**1.** **Arouter插件 节约2s**

**2.** **黑白屏主题**

**3.** **异步启动任务管理（重点图）**

**4.** **布局优化，**

> 1. AsyncLayoutInflate 不能用在有fragment的Act)，懒加载viewpager+fragment（比如我的页面viewstub）
> 2. X2C  编译时注解，不用解析xml，不用反射，不用io 。缺点：兼容性有问题，需要改源码。如TextView-AppcompatTextView

**5.** **idlehandler空闲加载**

**6.** **MultiDex字节方案（参考multidex章节）**



**问题1：100个线程执行完后进行打印**

> join()    wait/notify  juc?

**问题2：A线程有三步，B线程需等待A执行完第二步后执行**

> join()不可以，不管放哪都不行，得用wait/notify如下

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220722221225632.png" alt="image-20220722221225632" style="zoom: 70%;" />



**问题3：AB线程各三步，C线程需等待A第二步和B第二步都执行后再执行**

> 此时wait/notify不可以了，如下

```
public class Test {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threa1-0");
                synchronized (lock1) {
                    lock1.notify();
                }
                System.out.println("threa1-1");
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("threa2-0");
                synchronized (lock2) {
                    lock2.notify();
                }
                System.out.println("threa2-1");
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    try {
                        System.out.println("lock1_start");
                        //lock1等待中，那么下面lock2的同步代码块不会执行，必须等lock1唤醒后才能执行，但是下面代码会一直wait，等不到线程2的notify了
                        // （反之先锁lock2，如果线程1执行完后，才lock2.notify,那么lock会一直wait）
                        lock1.wait();
                        System.out.println("lock1_end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (lock2) {
                    try {
                        System.out.println("lock2_start");
                        lock2.wait();
                        System.out.println("lock2_end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("thread3");
            }
        });
        thread3.start();
        thread2.start();
        thread1.start();
    }
}
输出
lock1_start
threa2-0
threa2-1
threa1-0
threa1-1
lock1_end
lock2_start
```

不过可以用一把锁完成，如下：

```java
public class Test2 {
    private static int count = 2;
    public static void main(String[] args) {
        Object lock1 = new Object();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    System.out.println("threa1-0");
                    count--;
                    if(count == 0)
                        lock1.notify();
                    System.out.println("threa1-1");
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    System.out.println("threa2-0");
                    count--;
                    if(count == 0)
                        lock1.notify();
                    System.out.println("threa2-1");
                }
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    try {
                        System.out.println("lock1_start");
                        lock1.wait();
                        System.out.println("lock1_end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("thread3");
            }
        });
        thread3.start();
        thread2.start();
        thread1.start();
    }
}
```

但是以上如果叠加更多的任务，那么不好维护，会有很多wait，但是我们可以用CountDownLatch来实现上面的计数思路。

```java
public static void main(String[] args) {
    CountDownLatch countDownLatch = new CountDownLatch(2);
    Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }
    });
    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            countDownLatch.countDown();
        }
    });
    Thread thread3 = new Thread(new Runnable() {
        @Override
        public void run() {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    });
    thread3.start();
    thread2.start();
    thread1.start();
}
```

实战AppStartUp

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220722212421222.png" alt="image-20220722212421222" style="zoom:67%;" />

![](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220722212504408.png)

第一步：拓扑排序

建立顺序关系，建立表方便后续notify对应task

第二步：CountDownLunch

优化：1 添加多个任务，重复代码太多，可以参考arouter或者字节码插装

​			2 参考leakcanary，不需要application注册，contentprovider启动时机（小米面试），配置最后一任务，可以查出之前的task

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220724175757095.png" alt="image-20220724175757095" style="zoom:67%;" />

```
	    3 如果任务2是主线程执行，那么以上做法不够完美，3号会一直等在那，子线程和主线程分开add
```

可以参考源码更全源码https://github.com/idisfkj/android-startup



**启动速度优化**

阿里巴巴 [历时1年，上百万行代码！首次揭秘手淘全链路性能优化（上）-阿里云开发者社区 (aliyun.com)](https://developer.aliyun.com/article/710466) alpha库

<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image131.png" alt="img" style="zoom:50%;" /> 





---







#### 内存优化

**内存泄漏**

handler

static

匿名内部类 非静态内部类



> 解决本质：断开GcRoot

场景：  启动一个透明主题的activity，当前页面有个自定义view带动画，不可见时才会暂停， 动画执行导致idlehandler不执行，mnewactivity不会赋为空，主线程一直在更新ui没有空闲。

![image-20220809223559866](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220809223559866.png)

**解决：注意原Activity的生命周期只会执行onPause，不会执行onStop，所以还是可见状态，其中的动画关闭时机不能放在onStop或者onVisiable中，得放在onPause中去关闭。**

内存泄漏三方工具：LeakCanary   KOOM   Matrix



**内存抖动**

> 解决本质：避免频繁创建对象
>
> 1.代码中去除可以重复创建的对象
>
> 2.对象池复用: 如：Message
>
> 3.byte数组:参考Glide的LruArrayPool Int作为key会有自动拆装箱过程，导致Integer对象倍增，所以参考SparseArray双数组实现

![image-20220809223303319](https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image-20220809223303319.png)





**图片如何优化**

>





---



#### 卡顿优化

**systrace**

工具使用

**线上release的app？**

> 反射操作setAppTracingAllowed

![image-20220818222938623](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220818222938623.png)



#### 瘦身优化

**1.图片转webp**

**2.资源缩减、混淆**

```
minifyEnabled true 
shrinkResources true
```

**3.Lint分析器**

lint 工具不会扫描 assets/ 文件夹、通过反射引用的资源或已链接至应用的库文件。此外，它也不会移

除资源，只会提醒您它们的存在。 **与资源缩减不同，这里点击删除，那就是把文件删了。**

**native优化**

**4.so移除方案  **

ndk{abiFilters:}过滤，这个指令可以配置只打包你配置的so库，没有配置的就不打包，很灵活。

> **只适配armeabi**
> 优点:基本上适配了全部CPU架构（除了淘汰的mips和mips_64）
> 缺点：性能低，相当于在绝大多数手机上都是需要辅助ABI或动态转码来兼容
> **只适配 armeabi-v7a**
> 同理方案一，只是又筛掉了一部分老旧设备,在性能和兼容二者中比较平衡
> **只适配 arm64-v8**
> 优点: 性能最佳
> 缺点： 只能运行在arm64-v8上，要放弃部分老旧设备用户
>
> 大多数情况下我们可以只用一种armeabi-v7a，**但目前各大应用市场都要支持64位**，所以也会加上arm64-v8a。所以一般这样 abiFilters "armeabi-v7a", "arm64-v8a"，咱们项目就是，之前没有支持64，被小米打回
>
> Q1： 只适配了armeabi-v7a,那如果APP装在其他架构的手机上，如arm64-v8a上，会蹦吗？
> A: 不会，但是反过来会。

```
release { 
    ndk { abiFilters "armeabi", "armeabi-v7a", "arm64-v8a" 
    //有些观点是只留下armeabi即可，armeabi 目录下的 So 可以兼容别的平台上的 So， //但是，这样 别的平台使用时性能上就会有所损耗，失去了对特定平台的优化,而且近期国内的应 用市场也开始要求适配64位的应用了 } 
    }

```

**5.SoLoader**

主要的核心思想：我们为了降低包体积的大小，需要借助于远程的so下发,so下发到本地，有需要借助

jvm来架加载so。















#### 网络优化



---





#### ANR

<img src="/Users/AJiang/Library/Application Support/typora-user-images/image-20220819144332507.png" alt="image-20220819144332507" style="zoom:40%;" />



#### 编译优化aar

最好分支打包，出数据对比

https://mp.weixin.qq.com/s/SJ-0We7e8kK3btsrBKkZ4g









**4.14知识点：**

**Viewpager（默认预加载）  Viewpager2(默认懒加载)**

**lazyfragment**

**适配器模式**

**布局优化 viewstub**

**okhttp网络可以暂停吗 执行中不能，等待直接删除**























---









###  简历



熟悉APT Aspectj ASM Javaassist编译时技术



<img src="https://raw.githubusercontent.com/AndroidJiang/HomePics/master/typora/image208.png" alt="img" style="zoom:80%;" /> 

参考阿里，封装启动框架



项目中遇到的问题：

1 内存泄漏场景   启动一个透明主题的activity，当前页面有个自定义view带动画，不可见时才会暂停， 动画执行导致idlehandler不执行，mactivitys没有赋值null，持有引用



2 idlehandler 空闲线程以前都正常执行，后来有一天不执行了，必须切换tab才会去执行，loop中打印日志，发现挑战区有个自动滚动的列表，10ms就重新scrollto一次，一直绘制，没有空闲线程

```
Looper.myLooper().setMessageLogging(new LogPrinter(Log.DEBUG,LOG_TAG));

日志
<<<<< Finished to Handler (android.view.ViewRootImpl$ViewRootHandler) {c5afe55} cn.emagsoftware.gamehall.widget.recyclerview.AutoPollRecyclerView$AutoPollTask@e844865
 >>>>> Dispatching to Handler (android.view.ViewRootImpl$ViewRootHandler) {c5afe55} com.migugame.home_module.ui.adapter.recommend.HomeChallengeAdapter$7$1@dcfb12e: 0
```





说一个你认为你解决的最难的问题









1. compileSDK 和 targetSDKVersion区别
2. 说一下今日头条屏幕适配的原理
3. 说一下组件化路由表底层怎么做的
4. 说一下隐私合规你们改了哪些内容
5. 屏幕适配dp和sp有什么区别, dins干什么用的
6. 说一下你们公司降级SDK怎么实现的?
7. 你们一周发几个版本, 怎么保证一周两个版本不影响客户
8. 说一下白屏监测原理
9. 说一下RecycleView三级缓存
10. 说一下R8和D8的区别
11. 说一下如何对不同手机进行等级划分
12. 说一下ASM、AspectJ和AST的区别
13. 说一下你们项目的参数配置系统设计流程
14. 如何自定义一个gradle Plugin

小米 面
一面：
glide缓存
glide设置565有效吗
glide和其他图片库如何设计切换
几种图片库选择 根据哪些因素（就是区别）
okhttp源码 穿插问问题
性能优化  启动优化等
小米应用市场怎么去更新app  选择时间段  
手写代码 string转int  
你常用的设计模式
说一下观察者模式
内存泄露场景  handler如何避免  oom
平时如果解决bug的
为什么如此安全的https协议却仍然可以被抓包呢？

二面：
volite和sysnch
ipc几种方式  
intent传递大小有限制吗
activity之间传递大数据怎么处理
contentprovider
applicaton和provider的oncreate先后执行关系
glide三级缓存  placehold error fockback几个区别  后两个不设置 图片请求错误 怎么展示
okhttp拦截器几个说一下 拦截器中interceptor和networkinteceptor区别  重定向后在哪个里面返回
oom如何分析  mat工具如何使用等等 问的比较深
1<-2<-3  任务2依赖1结果 3依赖2结果   1 2都是异步  如何做
后台定时任务如何去做 如何在某个时间点做任务
service8.0问题  
子线程handler的looper在哪
jetpack了解吗

小米（王均友）

1、app性能优化，先说说启动优化
appstartup原理， 启动优化评测纬度或者说技术手段，除了打日志，启动场景分为哪些场景，热启动，冷启动，温启动
页面优化怎么做的？
掉帧分析，app的卡顿，耗时，blockcanary原理，有没有自己根据需求定制
2、Android 跨进程通信，为什么会用binder
binder的服务管理
3、view的绘制流程
4、网络优化，为什么要做网络优化，具体做了什么网络优化
5、recyclerview整个复用机制，里面的绘制机制，layoutmanager几个步骤，每个步骤里面大概做了什么
6、应用层绘制滞后卡顿，从系统整体上去解释一下
7、除了binder，还有哪些跨进程机制，操作系统层面来说
socket到底是一个什么样的东西
8、链表排序（力扣148）



【2023-05-31模拟面试题】
1简单自我介绍
2三个项目中哪个最有技术挑战
3青模项目中有没有使用抖音中台提供的插件化框架
4做插件化这件事上有什么收获
5下包的流程，比如说怎么构建出来一个插件包
6那你知道插件化使用的是AAB bundle方案还是hook方案还是其他方案
7下发的是一个个dex文件吗
8当我们打出来一个APK,有没有包含Activity这个类（正常打包，Framework的Activity)或者说Service
会打到我们的APK

> 不包含。Framework 层的类不会被打包进 APK 的主要原因是，这些类已经存在于 Android 设备的操作系统中。Android 操作系统的核心组件和框架层类是在设备的系统镜像中预装的，并且是由设备制造商或 Android 官方提供的。

9 Activity没有打包到APK,为什么我们可以使用到这个类呢

> 当我们在应用程序中使用 Activity 类时，我们实际上是利用了 Android 系统提供的 Activity 类的功能和特性。Android 框架负责管理 Activity 的生命周期、用户界面交互和其他与 Activity 相关的功能。这些功能和特性是通过 Android 操作系统中的 Framework 层实现的。

10OOM崩溃
11有处理过内存优化相关的事情没
12内存优化掌握到什么程度
13哪一个方面或技术领域研究的多一些
14关于网络这一块的理解
15 OKHttpi源码了解哪些
16 OKHttp设计的精髓在哪里
17简单说下OKHttp一个网络请求的流程
18 OKHttp的缓存设计
9能具体说一下服务端返回没有更新，服务端怎么判断客户端的结果有没有更新
20 OKHttp连接池作用是什么
21假设一个请求百度API,一个请求腾讯APi,连接池可以复用吗

> 当你使用 OkHttp 发送请求到不同的域名（例如百度和腾讯），实际上是使用了不同的主机名。在这种情况下，OkHttp 会为每个不同的主机名维护一个独立的连接池。这意味着不同域名的请求会使用不同的连接池，连接不会跨域名进行复用。

22连接池怎么复用
23网络相关的优化
24如何实现HTTPS建连，回传像HTTP一样快（连接复用没有生效时怎么做到呢，能不能通过空间换时间
实现)
25有没有做过弱网优化

> 1.设置超时；2.适当重试，错误反馈，监听网络恢复的时候重新请求；3.数据压缩；4.离线缓存数据

26拿到ANR trace,文件很大，怎么入手分析ANR
27ANR堆栈不明确，怎么定位原因
28多线程并发在实际开发中用到多吗
29 HashMap:是线程安全的吗
30多线程下使用HashMap,如何保证线程安全
31 ConcurrentHashMap如何保证线程安全
32 ConcurrentHashMap:去get元素的时候有加锁吗

> get 方法不需要加锁。因为 Node 的元素 value 和指针 next 是用 volatile 修饰的，在多线程环境下线程A修改节点的 value 或者新增节点的时候是对线程B可见的。
> 这也是它比其他并发集合比如 Hashtable、用 Collections.synchronizedMap()包装的 HashMap 效率高的原因之一。

33经常翻墙学习国外新技术，学了啥
34有解决过崩溃相关问题吗
35 Object finalize了解吗，只要有GC就会调用吗
36 Object notify了解吗，自己平时写代码有用过吗
37反射性能耗在哪里

> ①　invoke方法参数是object，比如原来方法是int，则装箱，然后再拆箱
>
> ②　getdecaleredmethod要循环遍历，费时间
>
> ③　检查方法可见性
>
> ④　编译器无法进行动态代码的优化，比如内联