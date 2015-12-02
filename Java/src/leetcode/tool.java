package leetcode;

import java.util.HashSet;
import java.util.Set;

public class tool {
	
	/**
	 * Given an array of integers, find if the array contains any duplicates. 
	 * Your function should return true if any value appears at least twice in the array, 
	 * and it should return false if every element is distinct.
	 * @param nums 输入数组
	 * @return 是否含有重复值
	 */
	public static boolean containsDuplicate(int[] nums){
		
		if(nums.length == 0)
			return false;
		
		Set<Integer> tempSet = new HashSet<Integer>();
		
		for(int i : nums){
			if(tempSet.contains(i)){
				return true;
			}
			tempSet.add(i);
		}
		return false;
	}

	/**
	 * Excel Sheet Column Number
	 * Given a column title as appear in an Excel sheet, return its corresponding column number.
	 * For example:A -> 1	...
	 * 			   Z -> 26
	 * 			  AA -> 27
	 * 			  AB -> 28 
	 * @param 输入的字符串
	 * @return 列数
	 */
	public static int titleToNumber(String s) {
		
		if (s.length() == 0) {
			return 0;
		}
		
        char numbers[] = s.toCharArray();
        
        if (numbers.length > 7) {
			return Integer.MAX_VALUE;
		}
        
        long result = 0;
        int[] multipliers = {1,26,676,17576,456976,11881376,308915776};
        int multiplierIndex = 0;
        for (int i = numbers.length-1; i >= 0; i--) {
			result += (numbers[i] - 'A' +1) * multipliers[multiplierIndex++];
		}
        
        if (result > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		} else {
			return (int)result;
		}
        
    }

	/**
	 * Given a roman numeral, convert it to an integer.
	 * @param s a roman numera
	 * @return equal int value
	 */
    public static int romanToInt(String s) {  
        int positive = 0;
        int negtive = 0;
        for (int i = 0; i < s.length() ; i++) {
			int curValue = findKey(s.charAt(i));
			if (i < s.length()-1 && curValue < findKey(s.charAt(i+1))) {
				negtive += curValue;
			}else {
				positive += curValue;
			}
		}
        return positive - negtive;
  }
    
   private static int findKey(char c) {
	   switch (c) {
	   	case 'I':
	   		return 1;
	   	case 'V':
	   		return 5;
	   	case 'X':
	   		return 10;
	   	case 'L':
	   		return 50;
	   	case 'C':
	   		return 100;
	   	case 'D':
	   		return 500;
	   	case 'M':
	   		return 1000;
	   	default:
	   		return -1;
	   }
   }
   
   /**
    * Given an array of integers, every element appears three times except for one. Find that single one.
    * @param input array
    * @return the number that appears only once
    */
   public static int singleNumber(int[] nums) {
	   int[] digits = new int[32];
	    for(int i=0; i<nums.length; i++){
	        int mask = 1;
	        for(int j=31; j>=0; j--){
	            if((mask & nums[i])!=0)
	                digits[j] ++;
	            mask <<= 1;
	        }
	    }
	    int res = 0;
	    for(int i=0; i<32; i++){
	        if(digits[i]%3==1)
	            res += 1;
	        if(i==31)
	            continue;
	        res <<= 1;
	    }
	    return res;
   }
	/**
	 * Given an integer, convert it to a roman numeral.
	 * @param num interger
	 * @return correspond string value
	 */
   public String intToRoman(int num) {
	   StringBuffer result = new StringBuffer();
	   if (num>=1000) {
		   result.append(repeat(num/1000, 'M'));
		   num %= 1000;
	   }
	   if (num >= 100) {
		   result.append(romanValue(num/100, "CD", "CM", "D", 'C'));
		   num %= 100;
	   }
	   if (num>= 10) {
		   result.append(romanValue(num/10, "XL", "XC", "L", 'X'));
		   num %= 10;
	   }
	   if (num >0) {
		   result.append(romanValue(num, "IV", "IX", "V", 'I'));
	   }
	   return result.toString();
   }
   private String repeat(int times, char ch) {
		 StringBuffer sb = new StringBuffer();
		 for (int i = 0; i < times; i++) {
			sb.append(ch);
		}
		 return sb.toString();
	}
   private String romanValue(int i,String four,String nine,String five,char one){
	   if (i == 4) {
			return four;
		}else if (i == 9) {
			return nine;
		}else {
			String temp = "";
			if (i/5 == 1) {
				temp = five;
			}		
			return temp+repeat(i%5, one);
		}
   }
   
   /**
    * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), 
    * prove that at least one duplicate number must exist. 
    * Assume that there is only one duplicate number, find the duplicate one.
    * @param nums
    * @return
    */
   public int findDuplicate(int[] nums) {
       //投票法（不满足常数的空间要求）
	   int votes[] = new int[nums.length-1];
	   for (int i : nums) {
		   if (votes[i-1]++ > 0) {
			   return i;
		   }
	   }
	   return -1;
   }
   public int findDuplicate2(int[] nums) {
       //追及法（时间过长）
	   int slowIndex = 0;
	   int fastIndex = 1;
	   while(nums[fastIndex++] != nums[slowIndex++]){
		   slowIndex %= nums.length;
		   fastIndex = ++fastIndex % nums.length;
		   if (fastIndex == slowIndex) {
			   fastIndex = ++fastIndex % nums.length;
		   }
		   System.out.println("curretn slow:"+slowIndex +"\t current fast:"+fastIndex);
	   }
	   return nums[--slowIndex];
   }
   public int findDuplicate3(int[] nums) {
       //追及法（改进）
	   int slow = 0;
	   int fast = 0;
	   int finder = 0;
	   while(true){
		   slow = nums[slow];
		   fast = nums[nums[fast]];
		   if (slow == fast) {
			   break;
		   }
	   }
	   while(true){
		   slow = nums[slow];
		   finder = nums[finder];
		   if ( slow == finder) {
			   return slow;
		   }
	   }
   }
   
   /**
    * You are climbing a stair case. It takes n steps to reach to the top.
    * Each time you can either climb 1 or 2 steps. 
    * In how many distinct ways can you climb to the top?
    * @param n n-step stairs
    * @return	number of distinct ways that you can climb to the top
    */
   public int climbStairs(int n) {//排列组合
	   int ways = 0;
       for (int twoStepCount = 0; 2*twoStepCount <= n; twoStepCount++) {
    	   int oneStepCount = n - 2*twoStepCount;
    	   if (oneStepCount >= 0) {
    		   if (oneStepCount <= twoStepCount ) {
    			   ways += combinate(oneStepCount, oneStepCount+twoStepCount);
    		   }else {
    			   ways += combinate(twoStepCount, oneStepCount+twoStepCount);
    		   }		   
    	   }else {
    		   break;
    	   }
       }
       return ways;
   }
   
   private long combinate(int m , int n ) {
	   long result = 1;
	   for(int i = 1; i<= m; i++){
		   result =  result * (n - i + 1) / i;
	   }
	   return result;
   }
   
   public int climbStairs2(int n){//斐波那契数列
	   double root5 = Math.sqrt(5);
	   double fi = (1+root5)/2;
	   n++;
	   return (int)((Math.pow(fi, n) - Math.pow(-fi, -n))/root5);
   }

	/**
	 * Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
	 * @param nums
	 * @return
     */
	public int maxSubArray(int[] nums) {

	}
   
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		tool tool = new tool();
//		System.out.println(tool.combinate(14, 30));
		System.out.println("44-step stair has "+tool.climbStairs(44)+ " dictinct ways to climb");
		System.out.println("44-step stair has "+tool.climbStairs2(44)+ " dictinct ways to climb");
	}

}
