package datastruct;

import lombok.Data;

/**
 * 二叉查找树
 * http://www.cnblogs.com/yangecnu/p/Introduce-Binary-Search-Tree.html
 * 1. 若任意节点的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
 *
 * 2. 若任意节点的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
 *
 * 3. 任意节点的左、右子树也分别为二叉查找树。
 *
 * 4. 没有键值相等的节点（no duplicate nodes）。
 */
public class BinarySearchTreeSymbolTable<TKey extends Comparable, TValue> {
    private Node root;
    @Data
    private class Node
    {
        public Node Left ;
        public Node Right ;
        public int Number ;
        public TKey Key ;
        public TValue Value ;

        public Node(TKey key, TValue value, int number)
        {
            this.Key = key;
            this.Value = value;
            this.Number = number;
        }
    }

    public void Put(TKey key, TValue value)
    {
        root = Put(root, key, value);
    }

    private Node Put(Node x, TKey key, TValue value)
    {
        //如果节点为空，则创建新的节点，并返回
        //否则比较根据大小判断是左节点还是右节点，然后继续查找左子树还是右子树
        //同时更新节点的Number的值
        if (x == null) return new Node(key, value, 1);
        int cmp = key.compareTo(x.Key);
        if (cmp < 0) x.Left = Put(x.Left, key, value);
        else if (cmp > 0) x.Right = Put(x.Right, key, value);
        else x.Value = value;
        x.Number = Size(x.Left) + Size(x.Right) + 1;
        return x;
    }



    public  TValue Get(TKey key)
    {
        TValue result = null;
        Node node = root;
        while (node != null)
        {

            if (key.compareTo(node.Key) > 0)
            {
                node = node.Right;
            }
            else if (key.compareTo(node.Key) < 0)
            {
                node = node.Left;
            }
            else
            {
                result = node.Value;
                break;
            }
        }
        return result;
    }


    public  TKey GetMax()
    {
        TKey maxItem = null;
        Node s = root;
        while (s.Right != null)
        {
            s = s.Right;
        }
        maxItem = s.Key;
        return maxItem;
    }

    public  TKey GetMin()
    {
        TKey minItem = null;
        Node s = root;
        while (s.Left != null)
        {
            s = s.Left;
        }
        minItem = s.Key;
        return minItem;
    }

    private int Size(Node node)
    {
        if (node == null) return 0;
        else return node.Number;
    }



    /**
     * 查找Floor(key)的值就是所有<=key的最大值，相反查找Ceiling的值就是所有>=key的最小值
     */
    public TKey Floor(TKey key)
    {
        Node x = Floor(root, key);
        if (x != null) return x.Key;
        else return null;
    }

    private Node Floor(Node x, TKey key)
    {
        if (x == null) return null;
        int cmp = key.compareTo(x.Key);
        if (cmp == 0) return x;
        if (cmp < 0) return Floor(x.Left, key);
        else
        {
            Node right = Floor(x.Right, key);
            if (right == null) return x;
            else return right;
        }
    }


    public static void main(String[] args) {
        Integer[] as=  {10,5,8,2,6,4};
        BinarySearchTreeSymbolTable table = new BinarySearchTreeSymbolTable();
        for (Integer a : as) {
            table.Put(a,Integer.valueOf(a));
        }
        System.out.println(table);
    }
}