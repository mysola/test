package start;


public class RadixSort {
    private class Node {
        public int val;
        public Node next;

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    private int[] sizeTable = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};

    private int getDigit(int x, int d) {
        return (x / sizeTable[d]) % 10;
    }


    public Node createListFromArray(int[] array) {
        if (array == null || array.length == 0)
            return null;
        Node node = null, head = null;
        node = head = new Node(array[0], null);
        for (int i = 1; i < array.length; i++) {
            node.next = new Node(array[i], null);
            node = node.next;
        }
        return head;
    }

    public int maxDigitSizeInArray(int[] array) {
        int max = 0, temp = 0;
        for (int i : array) {
            temp = 0;
            while (sizeTable[temp++] < i) ;
            max = temp > max ? temp - 1 : max;
        }
        return max;
    }

    //若每次都对所有序列进行排序，从最高优先级（最高位）开始排序，一直到最低位优先级（个位），这样后面进行的排序会打乱前面的排序，会使排序失败
    //例如{08，63，83}对高位排序得{08，63，83}，对低位排序得{63，83，08}，排序失败
    //后面进行的排序会打乱（覆盖）前面的排序，隐含意义是后面的排序比前面的排序优先级高，所以打乱前面的排序也没关系，毕竟高位较大的数一定较大，而不管低位如何
    //例如{08，63，83}对低位排序得{63，83，08}，对高位排序得{08，63，83}，排序成功
    public Node sort(int[] array) {
        //每个桶由链表构成，共十个桶，head为头指针，end为尾指针
        Node[] head = new Node[10];
        Node[] end = new Node[10];
        int maxDigitSize = maxDigitSizeInArray(array), digitIndex = 0;
        Node curHead = createListFromArray(array);
        for (int i = 0; i < maxDigitSize; i++) {
            //分配桶
            while (curHead != null) {
                digitIndex = getDigit(curHead.val, i);
                if (head[digitIndex] == null) {
                    head[digitIndex] = curHead;
                    end[digitIndex] = curHead;
                } else {
                    end[digitIndex].next = curHead;
                    end[digitIndex] = end[digitIndex].next;
                }
                curHead = curHead.next;
            }
            //顺次收集每个桶中的元素（将每个非空桶首尾相连）,curHead指向收集好的链表头部
            int tempIndex = 0;
            for (tempIndex = 0; tempIndex < array.length && head[tempIndex] == null; tempIndex++) ;
            curHead = head[tempIndex];
            Node lastNotNullEnd = end[tempIndex++];
            for (; tempIndex < array.length; tempIndex++) {
                if (tempIndex < array.length && head[tempIndex] != null) {
                    lastNotNullEnd.next = head[tempIndex];
                    lastNotNullEnd = end[tempIndex];
                }
            }
            //置空收集好的链表尾指针
            lastNotNullEnd.next = null;
            //置空桶
            for (int j = 0; j < 10; j++) {
                head[j] = null;
                end[j] = null;
            }
        }
        return curHead;
    }
}
