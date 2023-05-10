package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Admin
 */
public class Puzzle {

    // trạng thái kết quả dạng ma trận
    public final List<List<String>> result = Arrays.asList(
            Arrays.asList("1", "2", "3"),
            Arrays.asList("4", "5", "6"),
            Arrays.asList("7", "8", ""));
    // trạng thái hiện tại dạng ma trận
    public List<List<String>> current = new ArrayList<>();
    // trạng thái kết quả dạng list
    public List<String> resultList = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "");
    // trạng thái hiện tại dạng list
    public List<String> currentList = new ArrayList<>();
    // tập các trạng thái
    public List<State> O = new ArrayList<>();
    // đường đi từ trạng thái ban đầu đến trạng thái đích
    public String way = "";

    // khởi tạo trạng thái ban đầu
    public void shuffle() {
        O = new ArrayList<>();
        way = "";
        currentList = new ArrayList<>();
        resultList.stream()
                .forEach(l -> {
                    currentList.add(l);
                });
        Collections.shuffle(currentList);
        int count = 0;
        for (int i = 0; i < currentList.size(); ++i) {
            if (currentList.get(i).equals("")) {
                continue;
            }
            for (int j = i + 1; j < currentList.size(); ++j) {
                if (currentList.get(j).equals("")) {
                    continue;
                }
                if (currentList.get(j).charAt(0) < currentList.get(i).charAt(0)) {
                    count++;
                }
            }
        }
        System.out.println(count);
        System.out.println(currentList);
        if (count % 2 == 1) {
            for (int i = 0; i < currentList.size() - 1; ++i) {
                if (currentList.get(i).equals("") || currentList.get(i + 1).equals("")) {
                    continue;
                }
                String tmp = currentList.get(i);
                currentList.set(i, currentList.get(i + 1));
                currentList.set(i + 1, tmp);
                break;
            }
        }
        setCurrent();
    }

    public void setCurrent() {
        current = Arrays.asList(
                Arrays.asList(currentList.get(0), currentList.get(1), currentList.get(2)),
                Arrays.asList(currentList.get(3), currentList.get(4), currentList.get(5)),
                Arrays.asList(currentList.get(6), currentList.get(7), currentList.get(8))
        );
    }

    // thực thi thuật toán A*
    public void run() {
        O = new ArrayList<>();
        State state = new State(currentList, 0, "");
        O.add(state);
        boolean check = state.check();
        System.out.println(check);
        while (!check && !O.isEmpty()) {
            List<State> Q = new ArrayList<>();
            int i = O.size() - 1;
            System.out.println(O.get(i));
            if (O.get(i).check()) {
                way = O.get(i).way;
                break;
            } else {
                if (O.get(i).canMoveUp()) {
                    Q.add(O.get(i).moveUp());
                }
                if (O.get(i).canMoveDown()) {
                    Q.add(O.get(i).moveDown());
                }
                if (O.get(i).canMoveLeft()) {
                    Q.add(O.get(i).moveLeft());
                }
                if (O.get(i).canMoveRight()) {
                    Q.add(O.get(i).moveRight());
                }
            }
            O.remove(i);
            for (int k = 0; k < Q.size(); ++k) {
                for (int j = k + 1; j < Q.size(); ++j) {
                    if (Q.get(k).n <= Q.get(j).n) {
                        Collections.swap(Q, k, j);
                    }
                }
            }
            for (int j = 0; j < Q.size(); ++j) {
                if (Q.get(j).n == Q.get(Q.size() - 1).n) {
                    O.add(Q.get(j));
                }
            }
        }
    }

    // di chuyển ô trống lên trên
    public void moveUp() {
        int y = -1;
        int x = -1;
        for (int i = 0; i <= 2; ++i) {
            for (int j = 0; j <= 2; ++j) {
                if (current.get(i).get(j).equals("")) {
                    y = i;
                    x = j;
                    break;
                }
            }
            if (x != -1 && y != -1) {
                break;
            }
        }
        String value = current.get(y - 1).get(x);
        current.get(y).set(x, value);
        current.get(y - 1).set(x, "");
    }
    
    // di chuyển ô trống xuống dưới
    public void moveDown() {
        int x = -1;
        int y = -1;
        for (int i = 0; i <= 2; ++i) {
            for (int j = 0; j <= 2; ++j) {
                if (current.get(i).get(j).equals("")) {
                    y = i;
                    x = j;
                    break;
                }
            }
            if (x != -1 && y != -1) {
                break;
            }
        }
        String value = current.get(y + 1).get(x);
        current.get(y).set(x, value);
        current.get(y + 1).set(x, "");
    }

    // di chuyển ô trống sang trái
    public void moveLeft() {
        int x = -1;
        int y = -1;
        for (int i = 0; i <= 2; ++i) {
            for (int j = 0; j <= 2; ++j) {
                if (current.get(i).get(j).equals("")) {
                    y = i;
                    x = j;
                    break;
                }
            }
            if (x != -1 && y != -1) {
                break;
            }
        }
        String value = current.get(y).get(x - 1);
        current.get(y).set(x, value);
        current.get(y).set(x - 1, "");
    }

    // di chuyển ô trống sang phải
    public void moveRight() {
        int x = -1;
        int y = -1;
        for (int i = 0; i <= 2; ++i) {
            for (int j = 0; j <= 2; ++j) {
                if (current.get(i).get(j).equals("")) {
                    y = i;
                    x = j;
                    break;
                }
            }
            if (x != -1 && y != -1) {
                break;
            }
        }
        String value = current.get(y).get(x + 1);
        current.get(y).set(x, value);
        current.get(y).set(x + 1, "");
    }
}
