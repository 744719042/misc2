package com.example.misc2;

import com.example.misc2.model.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LambdaTest {
    public static List<User> users = new ArrayList<User>() {
        {
            add(new User("刘备","唯贤唯德，能服于人", "蜀", 46));
            add(new User("诸葛亮","淡泊以明志，宁静以致远", "蜀", 27));
            add(new User("关羽", "安能与老兵同列", "蜀", 43));
            add(new User("赵云", "子龙一身是胆", "蜀", 39));
            add(new User("曹操", "宁教我负天下人，不教天下人负我", "魏", 54));
            add(new User("司马懿","老而不死是为贼", "魏", 30));
            add(new User("司马昭","司马昭之心路人皆知", "魏", 12));
            add(new User("孙权","生子当如孙仲谋", "吴", 27));
            add(new User("周瑜","既生瑜何生亮", "吴", 34));
            add(new User("吕蒙","士别三日当刮目相待", "吴", 28));
        }
    };

    @Test
    public void testMap() {
        Map<String, String> map = users.stream().collect(Collectors.toMap(User::getName, User::getDesc, (left, right) -> left));
        System.out.println(map);
    }

    @Test
    public void testTerminal() {
        Optional<Integer> result = users.stream().map(User::getAge).reduce((sum, item) -> sum + item);
        System.out.println(result.get());
        long count = users.stream().map(User::getAge).count();
        System.out.println(count);
        result = users.stream().map(User::getAge).min(Comparator.comparingInt(Integer::valueOf));
        System.out.println(result.get());
    }

    @Test
    public void testReduce() {
        int result = users.stream().map(User::getAge).collect(Collectors.reducing(0, (sum, item) -> sum + item));
        System.out.println(result);
        Optional<User> user = users.stream().collect(Collectors.maxBy(Comparator.comparing(User::getAge)));
        System.out.println(user.get());
        double averageint = users.stream().collect(Collectors.averagingInt(User::getAge));
        System.out.println(averageint);
    }

    @Test
    public void testGroupBy() {
        Map<String, List<User>> map = users.stream().collect(Collectors.groupingBy(User::getState, Collectors.toList()));
        System.out.println(map);

        Map<String, List<String>> userMap = users.stream().collect(Collectors.groupingBy(User::getState,
                Collectors.mapping(User::getName, Collectors.toList())));
        System.out.println(userMap);

        Map<String, Long> countMap = users.stream().collect(Collectors.groupingBy(User::getState, Collectors.counting()));
        System.out.println(countMap);
    }

    @Test
    public void testPartition() {
        Map<Boolean, List<String>> userPartition = users.stream().collect(Collectors.partitioningBy(user -> user.getState().equals("蜀"), Collectors.mapping(User::getName, Collectors.toList())));
        System.out.println(userPartition);

        Map<Boolean, Long> countPartition = users.stream().collect(Collectors.partitioningBy(user -> user.getState().equals("蜀"), Collectors.counting()));
        System.out.println(countPartition);
    }
}
