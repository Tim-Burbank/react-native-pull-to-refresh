//
//  Tangram.m
//  test
//
//  Created by Youssef on 2018/3/19.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "Tangram.h"

@interface Tangram()
@property (nonatomic) CGRect startFrame;
@end

@implementation Tangram

- (instancetype)initWithFrame:(CGRect)frame {
  self = [super initWithFrame:frame];
  if (self) {
    CGFloat twoR = 2*CircleR;
    self.refreshX = (RefreshWidth-twoR)/2;
    self.refreshY = (RefreshHeight-twoR)/2;
    self.refreshWidth = RefreshWidth;
    self.refreshHeight = RefreshHeight;
    self.refreshTriggerDistance = TriggerDistance;
    
    _startFrame = CGRectMake(self.refreshX, self.refreshY, twoR, twoR);
    CGFloat colors = 132.0/255.0;
    self.circle = [[UIView alloc] init];
    [self resetCircle];
    self.circle.backgroundColor = [UIColor clearColor];
    self.circle.clipsToBounds = YES;
    self.circle.layer.borderWidth = 1;
    self.circle.layer.borderColor = [UIColor colorWithRed: colors green:colors blue:colors alpha:0.5].CGColor;
    [self addSubview:self.circle];
    
    UIImage *image = [UIImage imageNamed:@"earth_bg" inBundle:REFRESHBUNDLE compatibleWithTraitCollection:nil];
    self.img1 = [[UIImageView alloc] initWithImage:image];
    
    self.scale = twoR/self.img1.frame.size.height;
    self.imageWidth = self.img1.frame.size.width*self.scale;
    
    self.img1.frame = CGRectMake(-self.imageWidth+self.circle.frame.size.width, 0, self.imageWidth, twoR);
    self.image1Layer = self.img1.layer;
    [self.circle addSubview:self.img1];
    
    self.img2 = [[UIImageView alloc] initWithImage:image];
    self.img2.frame = CGRectMake(self.img1.frame.origin.x-self.imageWidth, 0, self.imageWidth, twoR);
    self.image2Layer = self.img2.layer;
    [self.circle addSubview:self.img2];
  }
  return self;
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

- (void)startShining {
  if (self.circle.frame.origin.y < self.refreshY) {
    [UIView animateWithDuration:0.1 delay:0.0 options:UIViewAnimationOptionCurveEaseIn animations:^{
      [self fixCircle:self.refreshTriggerDistance];
    } completion:^(BOOL finished) {
      if (finished) {
        [self image1AddAnimation];
        [self image2AddAnimation];
      }
    }];
  } else {
    [self image1AddAnimation];
    [self image2AddAnimation];
  }
  
}

- (void)image1AddAnimation {
  CABasicAnimation *basicAni1 = [CABasicAnimation animation];
  basicAni1.keyPath = @"position";
  basicAni1.fromValue = [NSValue valueWithCGPoint:self.img1.center];
  basicAni1.toValue = [NSValue valueWithCGPoint:CGPointMake(self.img1.center.x+self.imageWidth, self.img1.center.y)];
  basicAni1.duration = 15;
  basicAni1.repeatCount = 1;
  basicAni1.removedOnCompletion = NO;
  basicAni1.fillMode = kCAFillModeForwards;
  basicAni1.delegate = self;
  basicAni1.timingFunction = [CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionLinear];
  
  [self.image1Layer addAnimation:basicAni1 forKey:@"image1LayerAnimation1"];
}

- (void)image2AddAnimation {
  CABasicAnimation *basicAni2 = [CABasicAnimation animation];
  basicAni2.keyPath = @"position";
  basicAni2.fromValue = [NSValue valueWithCGPoint:self.img2.center];
  basicAni2.toValue = [NSValue valueWithCGPoint:CGPointMake(self.img2.center.x+2*self.imageWidth-CircleR, self.img2.center.y)];
  basicAni2.duration = 30;
  basicAni2.repeatCount = 1;
  basicAni2.removedOnCompletion = NO;
  basicAni2.fillMode = kCAFillModeForwards;
  basicAni2.delegate = self;
  basicAni2.timingFunction = [CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionLinear];
  
  [self.image2Layer addAnimation:basicAni2 forKey:@"image2LayerAnimation2"];
}

- (void)animationDidStop:(CAAnimation *)anim finished:(BOOL)flag {
  if (flag) {
    if ([self.image2Layer animationForKey:@"image2LayerAnimation2"] == anim) {
      [self image1AddAnimation];
      [self image2AddAnimation];
    }
  }
}

- (void)draggingBeforeReleaseWithOffset:(CGFloat)offset {
  if (offset <= self.refreshTriggerDistance && offset > 0) {
    [self fixCircle:offset];
  } else if (offset <= 0) {
    [self resetCircle];
  }
}

- (void)releaseAndBeginRefreshing {
  [self startShining];
}

- (void)stopRefreshingAndBackToOrigin {
  dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.25 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    [self resetCircle];
  });
}

- (void)endAll {
  [self.image1Layer removeAllAnimations];
  [self.image2Layer removeAllAnimations];
}

- (void)resetCircle {
  if (CGRectEqualToRect(self.circle.frame, _startFrame)) {
    return;
  }
  self.circle.frame = _startFrame;
  self.circle.layer.cornerRadius = self.circle.frame.size.width/2;
}

- (void)fixCircle:(CGFloat)offset {
  CGFloat width = CircleR+(CircleR*offset/self.refreshTriggerDistance);
  CGFloat x = (self.refreshWidth-width)/2;
  CGFloat y = self.refreshY*offset/self.refreshTriggerDistance;
  self.circle.frame = CGRectMake(x, y, width, width);
  self.circle.layer.cornerRadius = width/2;
}

- (CGFloat)triggerDistance {
  return self.refreshTriggerDistance;
}

@end

