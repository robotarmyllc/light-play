/*
 * Placement of the robots.
 */
package com.robotarmy.leap;

import java.awt.Point;

/**
 *
 * @author mark
 */
public class RobotCoords {
    public static final int WIDTH = 30;
    public static final int HEIGHT = 15;
    
    public static final Point[] ROBOT = new Point[]{
        new Point(0,  0),  //  There is no zero robot.
        
        new Point(9,  5),  //  1  A
        new Point(9,  7),  //  2
        new Point(8,  8),  //  3
        new Point(7,  7),  //  4
        new Point(7,  5),  //  5
        new Point(8,  4),  //  6
        new Point(8,  6),  //  7
        
        new Point(6,  6),  //  8  B
        new Point(6,  8),  //  9
        new Point(5,  9),  //  10
        new Point(4,  8),  //  11
        new Point(4,  6),  //  12
        new Point(5,  5),  //  13
        new Point(5,  7),  //  14

        new Point(12,  4),  //  15  C
        new Point(12,  6),  //  16
        new Point(11,  7),  //  17
        new Point(10,  6),  //  18
        new Point(10,  4),  //  19
        new Point(11,  3),  //  20
        new Point(11,  5),  //  21

        new Point(11,  9),  //  22  D
        new Point(11, 11),  //  23
        new Point(10, 12),  //  24
        new Point( 9, 11),  //  25
        new Point( 9,  9),  //  26
        new Point(10,  8),  //  27
        new Point(10, 10),  //  28

        new Point( 8, 10),  //  29  E
        new Point( 8, 12),  //  30
        new Point( 7, 13),  //  31
        new Point( 6, 12),  //  32
        new Point( 6, 10),  //  33
        new Point( 7,  9),  //  34
        new Point( 7, 11),  //  35

        new Point(15,  3),  //  36  F
        new Point(15,  5),  //  37
        new Point(14,  6),  //  38
        new Point(13,  5),  //  39
        new Point(13,  3),  //  40
        new Point(14,  2),  //  41
        new Point(14,  4),  //  42

        new Point(15,   8),  //  43  G
        new Point(15,  10),  //  44
        new Point(14,  11),  //  45
        new Point(13,  10),  //  46
        new Point(13,   8),  //  47
        new Point(14,   7),  //  48
        new Point(14,   9),  //  49

        new Point(18,   4),  //  50  H
        new Point(18,   6),  //  51
        new Point(17,   7),  //  52
        new Point(16,   6),  //  53
        new Point(16,   4),  //  54
        new Point(17,   3),  //  55
        new Point(17,   5),  //  56

        new Point(19,  9),  //  57  I
        new Point(19, 11),  //  58
        new Point(18, 12),  //  59
        new Point(17, 11),  //  60
        new Point(17,  9),  //  61
        new Point(18,  8),  //  62
        new Point(18, 10),  //  63

        new Point(22, 10),  //  64  J
        new Point(22, 12),  //  65
        new Point(20, 13),  //  66
        new Point(19, 12),  //  67
        new Point(19, 10),  //  68
        new Point(20,  9),  //  69
        new Point(20, 11),  //  70

        new Point(21,  5),  //  71  K
        new Point(21,  7),  //  72
        new Point(20,  8),  //  73
        new Point(19,  7),  //  74
        new Point(19,  5),  //  75
        new Point(20,  4),  //  76
        new Point(20,  6),  //  77

        new Point(21,  6),  //  78  L
        new Point(21,  8),  //  79
        new Point(20,  9),  //  80
        new Point(19,  8),  //  81
        new Point(19,  6),  //  82
        new Point(20,  5),  //  83
        new Point(20,  7),  //  84
    };
    
    // Static class!
    private RobotCoords() {}
    
}
