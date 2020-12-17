//
//  Tangram.h
//  test
//
//  Created by Youssef on 2018/3/19.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "QSRefreshMotionProtocol.h"

#define REFRESHBUNDLE_NAME   @"RefreshSource.bundle"
#define REFRESHBUNDLE_PATH   [[NSBundle mainBundle] pathForResource:@"RefreshSource" ofType:@"bundle"]
#define REFRESHBUNDLE        [NSBundle bundleWithPath: REFRESHBUNDLE_PATH]

#define RefreshWidth 80.0
#define RefreshHeight 80.0
#define TriggerDistance 90.0

#define CircleR   14.0

@interface Tangram : UIView
- (instancetype)initWithFrame:(CGRect)frame;
@end

@interface Tangram() <QSRefreshMotionProtocol, CAAnimationDelegate>
@property (nonatomic, strong) UIView * circle;
@property (nonatomic, strong) CALayer * image1Layer;
@property (nonatomic, strong) CALayer * image2Layer;
@property (nonatomic, strong) UIImageView * img1;
@property (nonatomic, strong) UIImageView * img2;
@property (nonatomic) CGFloat scale;
@property (nonatomic) CGFloat imageWidth;
@property (nonatomic) CGRect img1BaseFrame;
@property (nonatomic) CGRect img2BaseFrame;

@property(nonatomic) CGFloat refreshX;
@property(nonatomic) CGFloat refreshY;
@property(nonatomic) CGFloat refreshWidth;
@property(nonatomic) CGFloat refreshHeight;
@property(nonatomic) CGFloat refreshTriggerDistance;

@end
