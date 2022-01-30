import edu.princeton.cs.algs4.ResizingArrayStack;

public class StackWithMax {
    Float curMax;
    ResizingArrayStack<Float> data;
    ResizingArrayStack<Float> maximums;

    public StackWithMax() {
        curMax = null;
        data = new ResizingArrayStack<>();
        maximums = new ResizingArrayStack<>();
    }

    public void push(float val) {
        data.push(val);
        if (curMax == null) {
            maximums.push(val);
            curMax = val;
        } else {
            if (curMax >= val) {
                maximums.push(curMax);
            } else {
                maximums.push(val);
                curMax = val;
            }
        }
    }

    public float pop() {
        maximums.pop();
        return data.pop();
    }

    public float max() {
        return maximums.peek();
    }

    public static void main(String[] args) {
        StackWithMax sm = new StackWithMax();
        sm.push(3.3f);
        sm.push(13.3f);
        sm.push(5.8f);
        System.out.println("current max is " + sm.max());
        sm.pop();
        sm.pop();
        System.out.println("current max is " + sm.max());
    }
}
