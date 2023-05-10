package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Admin
 */
// lớp đại diện cho 1 trạng thái
public class State {

    // trạng thái hiện tại dưới dạng list
    List<String> currentState = new ArrayList<>();
    // trạng thái hiện tại dưới dạng ma trận
    List<List<String>> currentBlock = new ArrayList<>();
    // chi phí tới trạng thái hiện tại
    int g = 0;
    // giá trị hàm heuristic: số ô đặt sai chỗ
    int h = 0;
    // hàm f(n) = g(n) + h(n)
    int n = 0;
    // đường đi tới trạng thái hiện tại
    String way = " ";
    int x = -1;
    int y = -1;

    // khởi tạo 1 trạng thái
    public State(List<String> currentState, int g, String way) {
        this.way = "";
        this.currentState = currentState;
        this.g = g;
        this.way += way;
        heuristic();
        this.n = this.h + this.g;
        setBlock();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (currentBlock.get(i).get(j).equals("")) {
                    y = i;
                    x = j;
                    break;
                }
            }
            if (x != -1 && y != -1) {
                break;
            }
        }

    }

    // tính giá trị hàm heuristic
    public void heuristic() {
        h = 0;
        for (int i = 0; i < 8; ++i) {
            if (!String.valueOf(i + 1).equals(currentState.get(i))) {
                this.h++;
            }
        }

    }

    // kiểm tra nếu ô trống có thể đi lên
    public boolean canMoveUp() {
        if(y - 1 >= 0 && 50 > n) {
            if(way.equals("")) return true;
            return way.charAt(way.length() - 1)  != 'd';
        }
        return false;
    }

    // kiểm tra nếu ô trống có thể đi xuống
    public boolean canMoveDown() {
        if(y + 1 <= 2 && 50 > n) {
            if(way.equals("")) return true;
            return way.charAt(way.length() - 1)  != 'u';
        }
        return false;
    }

    // kiểm tra nếu ô trống có thể sang trái
    public boolean canMoveLeft() {
        if(x - 1 >= 0 && 50 > n) {
            if(way.equals("")) return true;
            return way.charAt(way.length() - 1)  != 'r';
        }
        return false;
    }

    // kiểm tra nếu ô trống có thể sang phải
    public boolean canMoveRight() {
        if(x + 1 <= 2 && 50 > n) {
            if(way.equals("")) return true;
            return way.charAt(way.length() - 1)  != 'l';
        }
        return false;
    }

    // trạng thái mới sau khi di chuyển ô trống lên trên
    public State moveUp() {
        List<String> upState = new ArrayList<>();
        setBlock();
        String value = currentBlock.get(y - 1).get(x);
        currentBlock.get(y).set(x, value);
        currentBlock.get(y - 1).set(x, "");
        currentBlock.stream()
                .forEach(list -> {
                    list.stream().forEach(l -> {
                        upState.add(l);
                    });
                });
        return new State(upState, g + 1, way + "u");
    }

    // trạng thái mới sau khi di chuyển ô trống xuống dưới
    public State moveDown() {
        List<String> downState = new ArrayList<>();
        setBlock();
        String value = currentBlock.get(y + 1).get(x);
        currentBlock.get(y).set(x, value);
        currentBlock.get(y + 1).set(x, "");
        currentBlock.stream()
                .forEach(list -> {
                    list.stream().forEach(l -> {
                        downState.add(l);
                    });
                });
        return new State(downState, g + 1, way + "d");
    }

    // trạng thái mới sau khi di chuyển ô trống sang trái
    public State moveLeft() {
        List<String> leftState = new ArrayList<>();
        setBlock();
        String value = currentBlock.get(y).get(x - 1);
        currentBlock.get(y).set(x, value);
        currentBlock.get(y).set(x - 1, "");
        currentBlock.stream()
                .forEach(list -> {
                    list.stream().forEach(l -> {
                        leftState.add(l);
                    });
                });
        return new State(leftState, g + 1, way + "l");
    }

    // trạng thái mới sau khi di chuyển ô trống sang phải
    public State moveRight() {
        List<String> rightState = new ArrayList<>();
        setBlock();
        String value = currentBlock.get(y).get(x + 1);
        currentBlock.get(y).set(x, value);
        currentBlock.get(y).set(x + 1, "");
        currentBlock.stream()
                .forEach(list -> {
                    list.stream().forEach(l -> {
                        rightState.add(l);
                    });
                });
        return new State(rightState, g + 1, way + "r");
    }

    public void setBlock() {
        currentBlock = Arrays.asList(
                Arrays.asList(currentState.get(0), currentState.get(1), currentState.get(2)),
                Arrays.asList(currentState.get(3), currentState.get(4), currentState.get(5)),
                Arrays.asList(currentState.get(6), currentState.get(7), currentState.get(8))
        );
    }

    // kiểm tra nếu hàm heuristic = 0 thì đã đạt được trạng thái đích
    public boolean check() {
        return h == 0;
    }

    @Override
    public String toString() {
        return currentState + " # " + g + " # " + h + " # " + n + " # " + way + " # " + x + " # " + y;
    }
}
