# 忽略 Compose Material 的缺失警告
-dontwarn androidx.compose.material.**

# 忽略 slf4j 的缺失警告
-dontwarn org.slf4j.**

# 保留 kotlinx.serialization 生成的序列化类
-keep class **$$serializer { *; }
-keepclassmembers class **$$serializer { *; }
-keepattributes *Annotation*

# 保留 Kotlin 反射需要的元数据
-keepclassmembers class kotlin.Metadata { *; }