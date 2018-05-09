package com.cctv.ssfs.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 *
 * @author sanjin
 * @date 2018/5/3
 * 获取RecyclerView是否滚动到最后
 */

public class RecyclerViewScrollUtils {
    /**
     * 找到数组中的最大值
     * @param lastPositions
     * @return
     */
    private static int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 到底部返回true
     *
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView, int newState) {
        //当前RecyclerView显示出来的最后一个的item的position
        int lastPosition = -1;
        //当前状态为停止滑动状态SCROLL_STATE_IDLE时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                //通过LayoutManager找到当前显示的最后的item的position
                lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof LinearLayoutManager) {
                lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                lastPosition = findMax(lastPositions);
            }
            //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
            //如果相等则说明已经滑动到最后了
            if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }
}
