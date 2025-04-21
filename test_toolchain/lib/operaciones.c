#include <operaciones.h>

double add(double first, double second){
    return (first + second);
}

double substract(double first, double second){
    return (first - second);
}

double mutiply(double first, double second){
    return (first * second);
}

double divide(double first, double second){
    return (first / second);
}

double cosine(double first){
    //https://stackoverflow.com/questions/77723846/implementing-a-cosine-function-in-c
    first *= first;
    return 1 - first/2 * (1 - first/12 * (1 - first/30 * (1 - first/56 * (1 - first/90))));
}

double square_root(double first){
    //https://www.geeksforgeeks.org/c-program-to-find-square-root-of-a-given-number/
    int start = 0, end = first;
    int mid;
 
    // To store the answer
    double result;
 
    // To find integral part of square
    // root of number
    while (start <= end) {
 
        // Find mid
        mid = (start + end) / 2;
 
        // If number is perfect square
        // then break
        if (mid * mid == first) {
            result = mid;
            break;
        }
 
        // Increment start if integral
        // part lies on right side
        // of the mid
        if (mid * mid < first) {
          //first start value should be added to answer
          result=start;
          //then start should be changed
            start = mid + 1;
        }
 
        // Decrement end if integral part
        // lies on the left side of the mid
        else {
            end = mid - 1;
        }
    }
 
    // To find the fractional part
    // of square root upto 5 decimal
    float increment = 0.1;
    for (int i = 0; i < 5; i++) {
        while (result * result <= first) {
            result += increment;
        }
 
        // Loop terminates,
        // when ans * ans > number
        result = result - increment;
        increment = increment / 10;
    }
    return result;
}