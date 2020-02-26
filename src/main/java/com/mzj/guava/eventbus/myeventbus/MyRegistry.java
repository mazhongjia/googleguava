package com.mzj.guava.eventbus.myeventbus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <pre>
 *  总线注册的组织结构：主题---listener---listener中的method
 * topic1->subscriber-subscribe
 *       ->subscriber-subscribe
 *       ->subscriber-subscribe
 * topic2->subscriber-subscribe
 *       ->subscriber-subscribe
 *       ->subscriber-subscribe
 *
 * </pre>
 */
class MyRegistry
{

    /**
     * topic---listener
     */
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<MySubscriber>> subscriberContainer
            = new ConcurrentHashMap<>();

    public void bind(Object subscriber)
    {
        List<Method> subscribeMethods = getSubscribeMethods(subscriber);
        subscribeMethods.forEach(m -> tierSubscriber(subscriber, m));
    }

    public void unbind(Object subscriber)
    {

        subscriberContainer.forEach((key, queue) ->
                queue.forEach(s ->
                {
                    if (s.getSubscribeObject() == subscriber)
                    {
                        s.setDisable(true);//利用打标记方式来unbind，防止①从集合中删除的方式带来的性能问题②stream方式不能对集合中元素进行删除
                    }
                }));
    }

    public ConcurrentLinkedQueue<MySubscriber> scanSubscriber(final String topic)
    {
        return subscriberContainer.get(topic);
    }

    private void tierSubscriber(Object subscriber, Method method)
    {
        final MySubscribe mySubscribe = method.getDeclaredAnnotation(MySubscribe.class);
        String topic = mySubscribe.topic();
        subscriberContainer.computeIfAbsent(topic, key -> new ConcurrentLinkedQueue<>());
        subscriberContainer.get(topic).add(new MySubscriber(subscriber, method));

    }

    private List<Method> getSubscribeMethods(Object subscriber)
    {
        final List<Method> methods = new ArrayList<>();
        Class<?> temp = subscriber.getClass();
        while (temp != null)
        {
            Method[] declaredMethods = temp.getDeclaredMethods();
            Arrays.stream(declaredMethods)
                    .filter(m -> m.isAnnotationPresent(MySubscribe.class) && m.getParameterCount() == 1 && m.getModifiers() == Modifier.PUBLIC)//方法必须是①被我妈的注解修饰②方法参赛数量为1③public方法
                    .forEach(methods::add);
            temp = temp.getSuperclass();
        }
        return methods;
    }
}
