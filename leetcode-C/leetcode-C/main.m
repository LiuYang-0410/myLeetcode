//
//  main.m
//  leetcode-C
//
//  Created by liuyang on 15/10/26.
//  Copyright © 2015年 liuyang. All rights reserved.
//

#import <Foundation/Foundation.h>

struct TreeLinkNode {
    int val;
    struct TreeLinkNode *left, *right, *next;
};

#pragma mark 连接二叉树
/* Populating Next Right Pointers in Each Node */
void connectBinaryTree(struct TreeLinkNode *root){
    if (root == NULL) {
        return;
    }
    root->next = NULL;
    
    printf("%d next : %d\n",root->val,root->next?root->next->val:-1);
    
    struct TreeLinkNode *node = root;
    struct TreeLinkNode *leftMostNode = root->left;
    while (node->left && node->right) {
        
        node->left->next = node->right;
        
        printf("%d next : %d\n",node->left->val,node->left->next?node->left->next->val:-1);
        
        if (node->next) {
            node->right->next = node->next->left;
            printf("%d next : %d\n",node->right->val,node->right->next?node->right->next->val:-1);
            node = node->next;
        }else{
            node->right->next = NULL;
            printf("%d next : %d\n",node->right->val,node->right->next?node->right->next->val:-1);
            node = leftMostNode;
            leftMostNode = node->left;
        }
    }
}

#pragma mark 不同的二叉搜索树的数目
/* Unique Binary Search Trees */
int numTrees(int n) {
    
    int count[n][n];
    
    for (int dist = 0; dist < n; dist++) {
        for (int i = 0; i < n; i++) {
            int j = i + dist;
            if (j >= n) {
                break;
            }else{
                count[i][j] = 0;
                for (int root = i; root<=j; root++) {
                    int leftMax = root-1;
                    int rightMin = root+1;
                    int leftCount = 1;
                    int rightCount = 1;
                    if (leftMax > i) {
                        leftCount = count[i][leftMax];
                    }
                    if (rightMin < j) {
                        rightCount = count[rightMin][j];
                    }
                    printf("old count[%d][%d] :%d\n",i,j,count[i][j]);
                    count[i][j] += leftCount*rightCount;
                    printf("count[%d][%d] :%d\n",i,j,count[i][j]);
                }
            }
        }
    }
    return count[0][n-1];
}

#pragma mark Nim Game
/**
 *  Nim Game，递归，重复调用超时
 *
 *  @param n 一共n步
 *
 *  @return 是否能赢
 */
bool canWinNim(int n) {
    if (n <= 3) {
        return true;
    }
    bool iCanWin = false;
    for (int myStep = 1; myStep <= 3; myStep++) {
        iCanWin = !canWinNim(n-myStep);
        if (iCanWin) {
            return iCanWin;
        }
    }
    return iCanWin;
}
/**
 *  Nim Game 动态规划，n很大时空间耗费，采用三个位置轮换，解决
 *
 *  @param n 一共n步
 *
 *  @return 是否能赢
 */
bool canWinNim2(int n) {
    if (n <= 3) {
        return true;
    }
    bool w[3];
    w[0] = w[1] = w[2] = true;
    for (int m = 4; m <= n; m++) {
        bool iCanWin = !w[0] + !w[1] + !w[2];  //有一个能赢即可
        w[0] = w[1];
        w[1] = w[2];
        w[2] = iCanWin;
    }
    return w[2];
}
/**
 *  Nim Game 找规律
 *
 *  @param n 一共n步
 *
 *  @return 是否能赢
 */
bool canWinNim3(int n) {
    return n%4;
}

#pragma mark 寻找插入点 Nim Game
int findSearchPosition(int* nums,int minIndex, int maxIndex, int target){
    
    if (minIndex > maxIndex) {
        return minIndex;
    }
    if (minIndex == maxIndex) {
        if (nums[minIndex] >= target) {
            return minIndex;
        }else{
            return minIndex + 1;
        }
    }
    
    int pivotIndex = (maxIndex + minIndex)/2;
    int pivot = nums[pivotIndex];
    if (pivot == target) {
        return pivotIndex;
    }
    if (pivot < target) {
        //在右侧寻找
        return findSearchPosition(nums, pivotIndex + 1, maxIndex, target);
    }else{
        //在左侧寻找
        return findSearchPosition(nums, minIndex, pivotIndex-1, target);
    }
}
/**
 *  Search Insert Position
 *
 *  @param nums     给定数组
 *  @param numsSize 数组大小
 *  @param target   目标数
 *
 *  @return 目标数的位置，或者不存在时的插入位置
 */
int searchInsert(int* nums, int numsSize, int target) {
    return findSearchPosition(nums, 0, numsSize-1, target);
}
int searchInsert2(int* nums, int numsSize, int target) {
    int minIndex = 0;
    int maxIndex = numsSize-1;
    while (minIndex <= maxIndex) {
        int midIndex = (minIndex + maxIndex)/2;
        if (nums[midIndex] >= target) {
            maxIndex = midIndex -1;
        }else{
            minIndex = midIndex+1;
        }
    }
    return minIndex;
}


int main(int argc, const char * argv[]) {
    @autoreleasepool {
        // insert code here...
        int numsSize = 4;
        int a[] = {1,3,5,6};
        int *nums = a;
        printf("insert position is %d\n",searchInsert2(nums, numsSize, 7));
    }
    return 0;
}
