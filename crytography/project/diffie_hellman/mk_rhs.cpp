#include <iostream>
#include <vector> 
#include <fstream>

using namespace std;

int main() {
	string root;
	int n;
	cout << "root: ";
	cin >> root;
	cout << "n: ";
	cin >> n;

	ofstream oFile;
	oFile.open(root + "rhs.mtx");
	oFile << n << " " << 1 << " " << 1 << endl;
	oFile << n << " " << 1 << " " << 1 << endl;
	oFile.close();
}