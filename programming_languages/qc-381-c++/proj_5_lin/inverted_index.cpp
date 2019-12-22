#include <iostream>
#include <sstream>
#include <vector>
#include <map>
#include <string>
#include <fstream>
using namespace std;

//Author: En Lin
//cunyid: 23510362

// position is 0-based
class inverted_index {
	private:
	  map<string, vector<vector<int>>> index;
	  void remove_punct(string &str) {
	  	for (int i = 0, len = str.size(); i < len; i++) 
	    { 
	        // check whether parsing character is punctuation or not 
	        if (ispunct(str[i])) 
	        { 
	            str.erase(i--, 1); 
	            len = str.size(); 
	        }
	    }
	  }
    
  public:
  inverted_index (string path) {
  	ifstream infile(path);
    string line;
    int i = 0;
    while (std::getline(infile, line)) {
    	istringstream word_stream(line);
    	int j = 0;
    	while (word_stream) {
    		string subs;
    		word_stream >> subs;
    		remove_punct(subs);
    		if (!subs.empty()) {
    			std::map<string,vector<vector<int>>>::iterator itr = index.find(subs);
    			if (itr == index.end()) {
    				vector<vector<int>> temp;
    				temp.push_back(vector<int>({i, j}));
            index.insert({subs, temp});
    			} else {
    				itr->second.push_back(vector<int>({i, j}));
    			}
    		}
    		j++;
    	}
    	i++;
    }
  }

  void print() {
  	for (const auto& kv : index) {
  		cout << kv.first << " " << kv.second.size() << " [";
      for (vector<int> pair : kv.second) {
      	cout << "(" << pair[0] << "," <<pair[1] << "),";
      }
      cout << "]" << endl;
  	}
  }
};

int main() {
  inverted_index index ("./input.txt");
  index.print();
}