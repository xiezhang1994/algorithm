package com.xz.github.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * 堆排序示例
 *
 * @author xz 2023/7/20
 */
public class HeapSortTest {
    public static void main(String[] args) {
        // 构建测试数据
        int arrLen = 10;
        int testCount = 10000;
        int[][] testData = new int[testCount][arrLen];
        Random random = new Random();
        for (int i = 0; i < testCount; i++) {
            int[] arr = new int[arrLen];
            for (int j = 0; j < arrLen; j++) {
                arr[j] = random.nextInt(500);
            }
            testData[i] = arr;
        }

        // 测试排序算法
        long costT = 0;
        SortType sortType = SortType.DESC;

        for (int i = 0; i < testCount; i++) {
            int[] arr = testData[i];
            String beforeSortData = Arrays.toString(arr);
            long startT = System.currentTimeMillis();
            // 排序
            heapSort(arr, sortType);
            costT += System.currentTimeMillis() - startT;

            if (!isCorrectSort(arr, sortType)) {
                System.out.println("排序结果不正确!");
                System.out.println("排序前: " + beforeSortData + " 排序后: " + Arrays.toString(arr));
            }
        }

        System.out.println("测试次数:" + testCount);
        System.out.println("测试耗时:" + costT + "ms");
    }

    /**
     * 堆排序：支持升序或者降序
     */
    public static void heapSort(int[] arr, SortType sortType) {
        for (int len = arr.length; len > 0; len--) {
            buildHeap(arr, len, sortType);

            // 将堆顶元素和数组最后一元素位置交换
            int temp = arr[0];
            arr[0] = arr[len - 1];
            arr[len - 1] = temp;
        }
    }

    /**
     * 构建大顶堆（升序）或者小顶堆（降序）
     */
    private static void buildHeap(int[] arr, int len, SortType sortType) {
        // 从最后一个非叶子节点开始构建
        // 最大的叶子节点为：arr.length/2-1
        int i = len / 2 - 1;

        while (i >= 0) {
            // 左子节点
            int lIdx = 2 * i + 1;
            // 右子节点
            int rIdx = 2 * i + 2;
            int tempLrIdx = lIdx;

            // 没有左子节点则结束
            if (lIdx > len - 1) {
                continue;
            }

            // 如果有右子节点，从左右节点里面找较大者（升序）或者较小者（降序）
            if (rIdx <= len - 1) {
                if (SortType.ASC == sortType && arr[lIdx] < arr[rIdx]
                    || SortType.DESC == sortType && arr[lIdx] > arr[rIdx]) {
                    tempLrIdx = rIdx;
                }
            }

            // 左右节点和当前节点比较
            if (SortType.ASC == sortType && arr[i] < arr[tempLrIdx]
                || SortType.DESC == sortType && arr[i] > arr[tempLrIdx]) {
                int temp = arr[i];
                arr[i] = arr[tempLrIdx];
                arr[tempLrIdx] = temp;
            }

            i--;
        }
    }

    /**
     * 校验排序结果是否正确
     */
    private static boolean isCorrectSort(int[] arr, SortType sortType) {
        for (int i = 0; i < arr.length; i++) {
            if (i + 1 < arr.length - 1) {
                if (sortType == SortType.ASC && arr[i] > arr[i + 1]) {
                    return false;
                }

                if (sortType == SortType.DESC && arr[i] < arr[i + 1]) {
                    return false;
                }
            }
        }

        return true;
    }

    public enum SortType {
        /**
         * 升序
         */
        ASC,

        /**
         * 降序
         */
        DESC
    }
}
