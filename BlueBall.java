package com.test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author 大鱼七成饱
 * @date 创建时间 2024/2/15
 */
public class BlueBall {

    /**
     * 伪随机数对象
     */
    private static Random random = new Random();

    /**
     * 蓝球中奖个数
     */
    private static int MATCH_COUNT = 2;

    /**
     * 总的蓝球个数
     */
    private static int ALL_BLUE_COUNT = 12;

    public static void main(String[] args) {
        //数学公式计算概率
        //蓝球总的组合个数 C(12,2)，2个中2个C(2,2)
        BigDecimal twoSelectTwo = combination(2, 2);
        BigDecimal twSelectTwo = combination(12, 2);
        BigDecimal divide = twoSelectTwo.divide(twSelectTwo, 10, BigDecimal.ROUND_HALF_UP);
        System.out.println("蓝球全中概率:" + divide);

        //伪随机函数模拟
        play(10_0000);

    }

    public static void play(int numSimulations) {

        int winCount = 0;
        for (int i = 0; i < numSimulations; i++) {
            //输出一组中奖号码
            //  每次开奖时从1至12的号码中随机摇出2个号码，这2个号码组成当期开奖号码。
            final int count = 2;
            Set<Integer> winningNumbers = new HashSet<>(count);
            while (winningNumbers.size() < count) {
                int num = random.nextInt(ALL_BLUE_COUNT) + 1;
                winningNumbers.add(num);
            }

            //玩家选择2个号码
            Set<Integer> userSelectedBlueBalls = getUserSelectedBlueBalls();

            //是否匹配
            int ballMatch = countMatchingBalls(userSelectedBlueBalls, winningNumbers);
            if (ballMatch == MATCH_COUNT) {
                winCount++;
            }
        }

        // 输出结果
        System.out.println("在 " + numSimulations + " 次模拟中：");
        System.out.println("中2个个数： " + winCount);
        double number = (double) winCount / numSimulations;
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        //关闭科学计数法
        nf.setGroupingUsed(false);
        //定义保留几位小数
        nf.setMaximumFractionDigits(10);
        //避免出现科学计数
        String format = nf.format(number);
        System.out.println("中2个的概率：" + format);

    }


    //排列组合数组公式代码实现
    public static BigDecimal combination(int ni, int ki) {
        BigDecimal n = new BigDecimal(ni);
        BigDecimal k = new BigDecimal(ki);
        BigDecimal a = new BigDecimal(1);
        BigDecimal b = new BigDecimal(1);
        BigDecimal val = new BigDecimal(2);
        BigDecimal divide = n.divide(val);
        if (k.compareTo(divide) == 1) {
            k = n.subtract(k);
        }
        for (int i = 1; i <= k.intValue(); i++) {
            BigDecimal subtract = n.add(new BigDecimal(1)).subtract(new BigDecimal(i));
            a = a.multiply(subtract);
            b = b.multiply(new BigDecimal(i));
        }
        return a.divide(b);

    }


    /**
     * 返回玩家选择的2个球,范围1-12，不重复
     */
    private static Set<Integer> getUserSelectedBlueBalls() {
        Set<Integer> userBlueBalls = new HashSet<>();
        while (userBlueBalls.size() < MATCH_COUNT) {
            int num = random.nextInt(ALL_BLUE_COUNT) + 1;
            userBlueBalls.add(num);
        }
        return userBlueBalls;
    }


    /**
     * 匹配中了几个球
     *
     * @return 匹配个数
     */
    private static int countMatchingBalls(Set<Integer> userBalls, Set<Integer> winningBalls) {
        int count = 0;
        for (int ball : userBalls) {
            if (winningBalls.contains(ball)) {
                count++;
            }
        }
        return count;
    }

}
