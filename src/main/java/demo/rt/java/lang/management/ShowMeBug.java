package demo.rt.java.lang.management;// ## 请在下方进行输入 ( 支持Markdown、插入公式，点击上方按钮“环境说明”查看详情 )
//  给定100元，10个人来抢红包，10个人随机得到一些金额，请编写一个红包算法（编程语言为Java），入参为金额，人数，出参为随机数组，约束条件为最大红包不能超过总金额的90%。谢谢。
// 必须定义 `ShowMeBug` 入口类和 `public static void main(String[] args)` 入口方法
import java.math.BigDecimal;
public class ShowMeBug {
    public static void main(String[] args) {

        test(100, 5, (float) 0.9);
        test(100, 5, (float) 0.8);
        test(100, 10, (float) 0.9);
        test(100, 10, (float) 0.8);

    }

    public static void test(float money, int personNumber, float max) {
        System.out.println("开始计算");
        BigDecimal[] moneySplit = complate(money, personNumber, max);
        BigDecimal check = new BigDecimal(0);
        for (int i = 0; i < moneySplit.length; i++) {
            System.out.println("红包分发:" + moneySplit[i]);
            check = check.add(moneySplit[i]);
        }
        System.out.println("检查:" + check);
        System.out.println("结束计算");
    }

    /**
     * 预留10%的红包+达到人数减二的时候,剩余金额/2 -> 为了防止极端情况
     * 简单的先限制每次的金额要小于90% 大于等于则重新获取
     *
     * @param money        红包金额
     * @param personNumber 抢红包人数
     * @param max          最大百分比
     */
    public static BigDecimal[] complate(float money, int personNumber, float max) {

        double keep = money * 0.1;
        double used = money * 0.9;
        BigDecimal moneyBigDecimal = new BigDecimal(used);
        BigDecimal[] moneySplit = new BigDecimal[personNumber];
        //已经计算过的数量
        BigDecimal havedComplateValueSum = new BigDecimal(0);
        for (int i = 0; i < personNumber - 2; i++) {
            moneySplit[i] = getComplateValue(max, havedComplateValueSum, moneyBigDecimal);
            havedComplateValueSum = havedComplateValueSum.add(moneySplit[i]);
        }
        BigDecimal lastMoney = moneyBigDecimal.subtract(havedComplateValueSum).divide(new BigDecimal(2), 4);
        moneySplit[personNumber - 2] = lastMoney;
        moneySplit[personNumber - 1] = (moneyBigDecimal.subtract(havedComplateValueSum)).subtract(lastMoney).add(new BigDecimal(keep));
        return moneySplit;
    }

    /**
     * 计算红包随机，简单的获取小于90%的随机金额
     * -> 保留两位小数(为了防止0的出现,向上保留两位小数)
     *
     * @param max                   红包金额
     * @param havedComplateValueSum 已经分配的金额
     * @param moneyTotal            总金额¬
     */
    private static BigDecimal getComplateValue(float max, BigDecimal havedComplateValueSum, BigDecimal moneyTotal) {
        float random = 0;
        BigDecimal v = null;
        do{
            do {
                //获取随机数 -> 当随机数小于0.9,跳出
                random = (float) Math.random();
            } while (random >= max || random == 0);
            v = (moneyTotal.subtract(havedComplateValueSum)).multiply(new BigDecimal(random));
            v = v.setScale(2, 4);
        }while(v.compareTo(new BigDecimal(0)) == 0);
        return v;
    }


}
