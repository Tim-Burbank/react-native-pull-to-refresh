//
//  TangramRefreshControlManager.m
//  test
//
//  Created by Youssef on 2018/3/19.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "TangramRefreshControlManager.h"
#import "TangramRefreshControl.h"

@implementation TangramRefreshControlManager

RCT_EXPORT_MODULE();

- (TangramRefreshControl *)view {
  return [TangramRefreshControl new];
}

RCT_EXPORT_VIEW_PROPERTY(onRefresh, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(refreshing, BOOL)

RCT_EXPORT_VIEW_PROPERTY(refreshX, CGFloat)
RCT_EXPORT_VIEW_PROPERTY(refreshY, CGFloat)
RCT_EXPORT_VIEW_PROPERTY(refreshWidth, CGFloat)
RCT_EXPORT_VIEW_PROPERTY(refreshHeight, CGFloat)
RCT_EXPORT_VIEW_PROPERTY(refreshTriggerDistance, CGFloat)

- (dispatch_queue_t)methodQueue {
  return dispatch_get_main_queue();
}

@end
