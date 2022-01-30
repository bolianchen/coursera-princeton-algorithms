import edu.princeton.cs.algs4.*;


//TODO: array resizing, raise Exceptions

public class StackWithMax {
    float[] data;
    int curPos;
    int maxPos;

    public StackWithMax() {
        // ignore the resizing need
        data = new float[10];
        int curPos = 0;
        int maxPos = -1;
    }

    public void push(float val) {
        if (val > data[maxPos]) {
            maxPos = curPos;
        }
        data[curPos++] = val;
    }

    public float pop() {
        float result = data[--curPos];
        if (curPos == maxPos) {
            maxPos = findMaxIdx();
        }
        return result;
    }

    public float max() {
        return data[maxPos];
    }

    public int findMaxIdx() {
        int maxIdx = 0;
        for (int i=0; i<curPos; i=i+1) {
            if (data[i] > data[maxIdx]) {
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    public boolean isEmpty() {
        return (curPos == 0);
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
