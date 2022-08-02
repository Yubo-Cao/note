#include <iostream>
#include <string>

using namespace std;

int main(){
    int input;
    cout << "Enter an integer: ";
    cin >> input;

    string name;
    cout << "Enter your name: ";
    cin >> name;

    cout << "Hello, " << name << "!" << endl;
    cout << "Your integer is " << input << endl;
    return 0;
}