# ForgeAutoShutdown

[![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-green.svg)](https://www.minecraft.net/)
[![Forge](https://img.shields.io/badge/Forge-47.3.0+-orange.svg)](https://files.minecraftforge.net/)
[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://adoptium.net/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE.txt)
[![Build](https://github.com/MiXiaoAi/ForgeAutoShutdown/actions/workflows/ci.yml/badge.svg)](https://github.com/MiXiaoAi/ForgeAutoShutdown/actions/workflows/ci.yml)
[![Release](https://img.shields.io/github/v/release/MiXiaoAi/ForgeAutoShutdown)](https://github.com/MiXiaoAi/ForgeAutoShutdown/releases/latest)
[![Downloads](https://img.shields.io/github/downloads/MiXiaoAi/ForgeAutoShutdown/total)](https://github.com/MiXiaoAi/ForgeAutoShutdown/releases)

[English](#english) | [中文](#中文)

---

## English

A powerful Minecraft Forge server auto-shutdown mod that supports scheduled shutdown, idle detection, player voting, and server monitoring for multiple shutdown methods.

> Forked from: [targren/forgeautoshutdown](https://gitlab.com/targren/forgeautoshutdown)

### ✨ Features

#### 🕐 Scheduled Shutdown
- Support daily fixed time shutdown (e.g., 5:00 AM daily)
- Support uptime-based shutdown (e.g., after 24 hours of runtime)
- Automatic countdown broadcast before shutdown (default 5 times)
- Optional delay when players are online

#### 💤 Idle Auto-Shutdown (New Feature)
- Monitor server during specified time periods
- Start countdown when no players detected
- Automatic shutdown after set idle time
- Support cross-midnight time periods
- Perfect for saving server resources

#### 🗳️ Player Voting Shutdown
- Players can initiate shutdown votes
- All players participate in voting
- Configurable minimum voter count
- Configurable maximum "no" votes
- Vote cooldown to prevent abuse

#### 🔍 Server Watchdog
- Monitor server response status
- Detect low TPS situations
- Auto-restart when server hangs
- Support soft and hard shutdown
- ⚠️ Use with caution, may cause data loss

#### 🔄 Hot-Reload Configuration
- No server restart needed after config changes
- One-click reload all configuration items
- Real-time configuration status viewing
- Convenient for debugging and quick adjustments

### 📋 Requirements

| Item | Requirement |
|------|-------------|
| Minecraft | 1.20.1 |
| Forge | 47.3.0+ |
| Java | 17 |
| Installation | Server-side (Required)<br>Client-side (Optional, for localized messages) |

### 🎮 Commands

#### Player Commands
```
/shutdown              # Start shutdown vote
/shutdown yes          # Vote yes
/shutdown no           # Vote no
```

#### Admin Commands (Requires OP level 3)
```
/forgeautoshutdown reload    # Hot-reload configuration
/forgeautoshutdown status    # View current configuration status
```

### ⚙️ Configuration

Configuration file location: `world/serverconfig/forgeautoshutdown-server.toml`

#### Configuration Categories

| Category | Description |
|----------|-------------|
| `[Schedule]` | Scheduled shutdown configuration |
| `[Voting]` | Player voting shutdown configuration |
| `[Watchdog]` | Server watchdog monitoring configuration |
| `[IdleShutdown]` | Idle auto-shutdown configuration |
| `[Messages]` | Custom message text |

#### Idle Auto-Shutdown Configuration Example

```toml
[IdleShutdown]
    # Enable idle auto-shutdown
    Enabled = true
    
    # Monitoring time period (24-hour format)
    StartHour = 0        # Start hour (0-23)
    StartMinute = 0      # Start minute (0-59)
    EndHour = 23         # End hour (0-23)
    EndMinute = 59       # End minute (0-59)
    
    # Idle timeout settings
    IdleTimeout = 30     # Minutes to wait before shutdown (1-1440)
    CheckInterval = 1    # Check interval in minutes (1-60)
```

#### Usage Scenarios

**24/7 Monitoring Mode**
```toml
StartHour = 0
StartMinute = 0
EndHour = 23
EndMinute = 59
IdleTimeout = 30
```

**Night Energy-Saving Mode**
```toml
StartHour = 22
StartMinute = 0
EndHour = 6
EndMinute = 0
IdleTimeout = 15
```

**Business Hours Mode**
```toml
StartHour = 9
StartMinute = 0
EndHour = 18
EndMinute = 0
IdleTimeout = 60
```

### 🔧 Hot-Reload Feature

After modifying configuration files, no server restart needed:

1. Edit configuration file `world/serverconfig/forgeautoshutdown-server.toml`
2. Execute command in-game: `/forgeautoshutdown reload`
3. Configuration takes effect immediately

**Reload Process:**
- ✅ Stop all running tasks
- ✅ Re-read configuration file
- ✅ Validate configuration
- ✅ Start tasks based on new configuration

**Notes:**
- ⚠️ Hot-reload will reset task states (idle timers, voting status, etc.)
- ⚠️ Recommend reloading during server idle time
- ⚠️ Reload failures will show error messages in logs

### 📦 Installation

#### Method 1: Download from Releases (Recommended)
1. Visit [Releases page](https://github.com/MiXiaoAi/ForgeAutoShutdown/releases/latest)
2. Download the latest `forgeautoshutdown-1.20.1-1.1.0.jar`
3. Place the file in your server's `mods` folder
4. Start server to auto-generate configuration files
5. Edit configuration file `world/serverconfig/forgeautoshutdown-server.toml`
6. Execute `/forgeautoshutdown reload` or restart server to apply configuration

#### Method 2: Build from Source
Requires Java 17 or higher:

```bash
# Clone repository
git clone https://github.com/MiXiaoAi/ForgeAutoShutdown.git
cd ForgeAutoShutdown

# Build
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

Build output: `build/libs/forgeautoshutdown-1.20.1-1.1.0.jar`

### 📝 Changelog

#### v1.20.1-1.1.0
- ✨ Adapted to Minecraft 1.20.1
- ✨ Added idle auto-shutdown feature
- ✨ Added hot-reload configuration feature
- ✨ Added configuration status view command
- 🐛 Fixed server shutdown thread blocking issue
- 🔧 Upgraded Gradle to 8.11.1
- 🔧 Upgraded ForgeGradle to 6.0.x

### � License

This project is licensed under the [MIT License](LICENSE.txt)

### � Acknowledgments

- Original authors: [RoyCurtis](https://github.com/RoyCurtis), [Targren](https://gitlab.com/targren)
- Original project: https://gitlab.com/targren/forgeautoshutdown
- Current maintainer: [MiXiaoAi](https://github.com/MiXiaoAi)

### 💬 Feedback & Support

For issues or suggestions, please submit Issues or Pull Requests.

---

## 中文

一个功能强大的 Minecraft Forge 服务器自动关闭模组，支持定时关服、空闲检测、玩家投票和服务器监控等多种关服方式。

> Fork 自：[targren/forgeautoshutdown](https://gitlab.com/targren/forgeautoshutdown)

### ✨ 功能特性

#### 🕐 定时关服
- 支持每天固定时间关服（如每天凌晨 5:00）
- 支持按运行时长关服（如运行 24 小时后关服）
- 关服前自动广播倒计时提醒（默认 5 次）
- 有玩家在线时可选择延迟关服

#### 💤 空闲自动关服（新功能）
- 在指定时间段内监控服务器
- 检测到无人在线后开始计时
- 超过设定时间自动关闭服务器
- 支持跨午夜的时间段设置
- 适合节省服务器资源

#### 🗳️ 玩家投票关服
- 玩家可发起关服投票
- 全体玩家参与投票决定
- 可配置最少投票人数
- 可配置否决票数上限
- 投票冷却时间防止滥用

#### 🔍 服务器看门狗
- 监控服务器响应状态
- 检测 TPS 过低情况
- 服务器卡死时自动重启
- 支持软关服和硬关服
- ⚠️ 使用需谨慎，可能导致数据丢失

#### 🔄 热重载配置
- 修改配置后无需重启服务器
- 一键重载所有配置项
- 实时查看配置状态
- 方便调试和快速调整

### 📋 环境要求

| 项目 | 要求 |
|------|------|
| Minecraft | 1.20.1 |
| Forge | 47.3.0+ |
| Java | 17 |
| 安装位置 | 服务器端（必须）<br>客户端（可选，用于本地化消息） |

### 🎮 指令说明

#### 玩家命令
```
/shutdown              # 发起关服投票
/shutdown yes          # 投赞成票
/shutdown no           # 投反对票
```

#### 管理员命令（需要 OP 等级 3）
```
/forgeautoshutdown reload    # 热重载配置文件
/forgeautoshutdown status    # 查看当前配置状态
```

### ⚙️ 配置说明

配置文件位置：`world/serverconfig/forgeautoshutdown-server.toml`

#### 配置分类

| 分类 | 说明 |
|------|------|
| `[Schedule]` | 定时关服配置 |
| `[Voting]` | 投票关服配置 |
| `[Watchdog]` | 看门狗监控配置 |
| `[IdleShutdown]` | 空闲自动关服配置 |
| `[Messages]` | 消息文本自定义 |

#### 空闲自动关服配置示例

```toml
[IdleShutdown]
    # 是否启用空闲自动关服
    Enabled = true
    
    # 监控时间段（24小时制）
    StartHour = 0        # 开始小时（0-23）
    StartMinute = 0      # 开始分钟（0-59）
    EndHour = 23         # 结束小时（0-23）
    EndMinute = 59       # 结束分钟（0-59）
    
    # 空闲超时设置
    IdleTimeout = 30     # 空闲多少分钟后关服（1-1440）
    CheckInterval = 1    # 检测间隔（分钟，1-60）
```

#### 使用场景示例

**全天监控模式**
适合需要随时关闭空闲服务器的场景
```toml
StartHour = 0
StartMinute = 0
EndHour = 23
EndMinute = 59
IdleTimeout = 30
```

**夜间节能模式**
仅在夜间监控，白天不关服
```toml
StartHour = 22
StartMinute = 0
EndHour = 6
EndMinute = 0
IdleTimeout = 15
```

**工作时间模式**
仅在工作时间监控
```toml
StartHour = 9
StartMinute = 0
EndHour = 18
EndMinute = 0
IdleTimeout = 60
```

### 🔧 热重载功能

修改配置文件后，无需重启服务器：

1. 编辑配置文件 `world/serverconfig/forgeautoshutdown-server.toml`
2. 在游戏中执行命令：`/forgeautoshutdown reload`
3. 配置立即生效

**重载过程：**
- ✅ 停止所有正在运行的任务
- ✅ 重新读取配置文件
- ✅ 验证配置有效性
- ✅ 根据新配置启动任务

**注意事项：**
- ⚠️ 热重载会重置任务状态（空闲计时器、投票状态等）
- ⚠️ 建议在服务器空闲时进行重载
- ⚠️ 重载失败会在日志中显示错误信息

### 📦 安装方法

#### 方式一：从 Release 下载（推荐）
1. 访问 [Releases 页面](https://github.com/MiXiaoAi/ForgeAutoShutdown/releases/latest)
2. 下载最新版本的 `forgeautoshutdown-1.20.1-1.1.0.jar`
3. 将文件放入服务器的 `mods` 文件夹
4. 启动服务器，自动生成配置文件
5. 编辑配置文件 `world/serverconfig/forgeautoshutdown-server.toml`
6. 执行 `/forgeautoshutdown reload` 或重启服务器应用配置

#### 方式二：自行构建
需要 Java 17 或更高版本：

```bash
# 克隆仓库
git clone https://github.com/MiXiaoAi/ForgeAutoShutdown.git
cd ForgeAutoShutdown

# 构建
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

构建产物位于：`build/libs/forgeautoshutdown-1.20.1-1.1.0.jar`

### 📝 更新日志

#### v1.20.1-1.1.0
- ✨ 适配 Minecraft 1.20.1
- ✨ 新增空闲自动关服功能
- ✨ 新增热重载配置功能
- ✨ 新增配置状态查看命令
- 🐛 修复服务器关闭时线程阻塞问题
- 🔧 升级 Gradle 到 8.11.1
- 🔧 升级 ForgeGradle 到 6.0.x

### 📄 许可证

本项目采用 [MIT 许可证](LICENSE.txt)

### 🙏 致谢

- 原作者：[RoyCurtis](https://github.com/RoyCurtis), [Targren](https://gitlab.com/targren)
- 原项目地址：https://gitlab.com/targren/forgeautoshutdown
- 当前维护者：[MiXiaoAi](https://github.com/MiXiaoAi)

### 💬 反馈与支持

如有问题或建议，欢迎提交 Issue 或 Pull Request。