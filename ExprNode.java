/**
 **     Expression Node
 **/

public class ExprNode {

    int type, value;
    ExprNode left, right;

    public ExprNode(int ty, int v) {
    type = ty;
    value = v;
    left = right = null;
    }

    public ExprNode(int ty, ExprNode l, ExprNode r) {
    type = ty;
    value = 0;
    left = l;
    right = r;
    }

}

