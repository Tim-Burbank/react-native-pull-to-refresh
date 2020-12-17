//
//  TangramRefreshControl.m
//  test
//
//  Created by Youssef on 2018/3/19.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "TangramRefreshControl.h"
#import "Tangram.h"

#define MIN_FLOAT (0.0)

@implementation TangramRefreshControl

- (instancetype)init
{
  self = [super init];
  if (self) {
    self.refreshX = MIN_FLOAT;
    self.refreshY = MIN_FLOAT;
    self.refreshHeight = MIN_FLOAT;
    self.refreshWidth = MIN_FLOAT;
    self.refreshTriggerDistance = MIN_FLOAT;
    self.refreshView = [[Tangram alloc] initWithFrame:CGRectMake((self.frame.size.width-RefreshWidth)/2, 0, RefreshWidth, RefreshHeight)];
    self.refreshView.backgroundColor = [UIColor clearColor];
  }
  return self;
}

- (void)layoutSubviews {
  [super layoutSubviews];
  CGFloat width = RefreshWidth;
  CGFloat height = RefreshHeight;
  
  if (fabs(self.refreshWidth) > MIN_FLOAT) {
    width = self.refreshWidth;
    [(Tangram *)self.refreshView setRefreshWidth:self.refreshWidth];
  }
  if (fabs(self.refreshHeight) > MIN_FLOAT) {
    height = self.refreshHeight;
    [(Tangram *)self.refreshView setRefreshHeight:self.refreshHeight];
  }
  if (fabs(self.refreshX) > MIN_FLOAT) {
    [(Tangram *)self.refreshView setRefreshX:self.refreshX];
  }
  if (fabs(self.refreshY) > MIN_FLOAT) {
    [(Tangram *)self.refreshView setRefreshY:self.refreshY];
  }
  if (fabs(self.refreshTriggerDistance) > MIN_FLOAT) {
    [(Tangram *)self.refreshView setRefreshTriggerDistance:self.refreshTriggerDistance];
  }
  self.refreshView.frame = CGRectMake((self.frame.size.width-width)/2, 0, width, height);
}

- (void)setRefreshX:(CGFloat)refreshX {
  _refreshX = refreshX;
}

- (void)setRefreshY:(CGFloat)refreshY {
  _refreshY = refreshY;
}

- (void)setRefreshWidth:(CGFloat)refreshWidth {
  _refreshWidth = refreshWidth;
}

- (void)setRefreshHeight:(CGFloat)refreshHeight {
  _refreshHeight = refreshHeight;
}

- (void)setRefreshTriggerDistance:(CGFloat)refreshTriggerDistance {
  _refreshTriggerDistance = refreshTriggerDistance;
}

@end

