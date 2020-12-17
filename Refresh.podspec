
Pod::Spec.new do |s|
  s.name         = "Refresh"
  s.version      = "1.0.13"
  s.summary      = "Refresh"
  s.description  = <<-DESC
                    a
                   DESC
  s.homepage     = "https://github.com/Tim-Burbank/react-native-pull-to-refresh.git"
  s.license      = "MIT"
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/Tim-Burbank/react-native-pull-to-refresh.git", :tag => "1.0.13" }
  s.source_files  = "**/*.{h,m}"
  s.resource_bundle = {
    'RefreshSource' => [
      'ios/QSRefreshControl/earth_bg.png'
    ]
  }
  s.requires_arc = true
  s.dependency "React"
end


