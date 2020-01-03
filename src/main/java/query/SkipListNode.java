package query;

/**
 * @author wwy
 */
public class SkipListNode <T>{
     int key;
     T value;
     SkipListNode<T> up, down, left, right; // 上下左右 四个指针
     static final int HEAD_KEY = Integer.MIN_VALUE; // 负无穷
     static final int  TAIL_KEY = Integer.MAX_VALUE; // 正无穷
     SkipListNode(int k,T v) {
        key = k;
        value = v;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public T getValue() {
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }
    @Override
    public boolean equals(Object o) {
        if (this==o) {
            return true;
        }
        if (o==null) {
            return false;
        }
        if (!(o instanceof SkipListNode<?>)) {
            return false;
        }
        SkipListNode<T> ent;
        try {
            ent = (SkipListNode<T>)  o; // 检测类型
        } catch (ClassCastException ex) {
            return false;
        }
        return (ent.getKey() == key) && (ent.getValue() == value);
    }
    @Override
    public String toString() {
        return "key-value:"+key+"-"+value;
    }
}
