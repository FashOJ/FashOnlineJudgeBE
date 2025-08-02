#!/bin/bash

# FashOJ 微服务启动/停止脚本
# 使用方法: ./testApp.sh [start|stop]

# 定义服务列表（按启动顺序）
SERVICES=("gateway" "user-service" "problem-service" "submission-service" "judge-service")

# 定义端口映射
declare -A SERVICE_PORTS
SERVICE_PORTS["gateway"]="8080"
SERVICE_PORTS["user-service"]="8081"
SERVICE_PORTS["problem-service"]="8082"
SERVICE_PORTS["submission-service"]="8083"
SERVICE_PORTS["judge-service"]="8084"

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 日志函数
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查端口是否被占用
check_port() {
    local port=$1
    if lsof -Pi :$port -sTCP:LISTEN -t >/dev/null 2>&1; then
        return 0  # 端口被占用
    else
        return 1  # 端口未被占用
    fi
}

# 等待端口启动
wait_for_port() {
    local port=$1
    local service=$2
    local timeout=60
    local count=0
    
    log_info "等待 $service 服务启动 (端口: $port)..."
    
    while [ $count -lt $timeout ]; do
        if check_port $port; then
            log_success "$service 服务已启动 (端口: $port)"
            return 0
        fi
        sleep 2
        count=$((count + 2))
        echo -n "."
    done
    
    echo
    log_error "$service 服务启动超时 (端口: $port)"
    return 1
}

# 停止服务
stop_service() {
    local service=$1
    local port=${SERVICE_PORTS[$service]}
    
    log_info "正在停止 $service 服务..."
    
    if check_port $port; then
        # 查找并杀死占用端口的进程
        local pid=$(lsof -ti:$port)
        if [ ! -z "$pid" ]; then
            kill -9 $pid 2>/dev/null
            sleep 2
            
            if check_port $port; then
                log_error "无法停止 $service 服务 (PID: $pid)"
                return 1
            else
                log_success "$service 服务已停止"
                return 0
            fi
        fi
    else
        log_warning "$service 服务未运行"
        return 0
    fi
}

# 启动服务
start_service() {
    local service=$1
    local port=${SERVICE_PORTS[$service]}
    
    log_info "正在启动 $service 服务..."
    
    # 检查端口是否已被占用
    if check_port $port; then
        log_warning "$service 服务已在运行 (端口: $port)"
        return 0
    fi
    
    # 启动服务
    nohup mvn spring-boot:run -pl $service > logs/${service}.out 2>&1 &
    local mvn_pid=$!
    
    # 等待服务启动
    if wait_for_port $port $service; then
        echo $mvn_pid > logs/${service}.pid
        return 0
    else
        # 如果启动失败，杀死Maven进程
        kill -9 $mvn_pid 2>/dev/null
        return 1
    fi
}

# 启动所有服务
start_all() {
    log_info "开始启动 FashOJ 微服务..."
    
    # 创建logs目录
    mkdir -p logs
    
    local failed_services=()
    
    for service in "${SERVICES[@]}"; do
        if start_service $service; then
            # 服务间启动间隔
            if [ "$service" != "judge-service" ]; then
                log_info "等待 5 秒后启动下一个服务..."
                sleep 5
            fi
        else
            failed_services+=("$service")
        fi
    done
    
    echo
    if [ ${#failed_services[@]} -eq 0 ]; then
        log_success "所有服务启动成功！"
        echo
        log_info "服务访问地址:"
        for service in "${SERVICES[@]}"; do
            local port=${SERVICE_PORTS[$service]}
            echo "  - $service: http://localhost:$port"
        done
    else
        log_error "以下服务启动失败: ${failed_services[*]}"
        return 1
    fi
}

# 停止所有服务
stop_all() {
    log_info "开始停止 FashOJ 微服务..."
    
    # 反向停止服务
    local reversed_services=()
    for ((i=${#SERVICES[@]}-1; i>=0; i--)); do
        reversed_services+=("${SERVICES[i]}")
    done
    
    for service in "${reversed_services[@]}"; do
        stop_service $service
        sleep 1
    done
    
    # 清理PID文件
    rm -f logs/*.pid logs/*.out
    
    log_success "所有服务已停止"
}

# 显示服务状态
show_status() {
    log_info "FashOJ 微服务状态:"
    echo
    
    for service in "${SERVICES[@]}"; do
        local port=${SERVICE_PORTS[$service]}
        if check_port $port; then
            echo -e "  - $service: ${GREEN}运行中${NC} (端口: $port)"
        else
            echo -e "  - $service: ${RED}已停止${NC} (端口: $port)"
        fi
    done
}

# 显示帮助信息
show_help() {
    echo "FashOJ 微服务管理脚本"
    echo
    echo "使用方法:"
    echo "  $0 start    - 启动所有微服务"
    echo "  $0 stop     - 停止所有微服务"
    echo "  $0 restart  - 重启所有微服务"
    echo "  $0 status   - 显示服务状态"
    echo "  $0 help     - 显示帮助信息"
    echo
    echo "服务列表:"
    for service in "${SERVICES[@]}"; do
        local port=${SERVICE_PORTS[$service]}
        echo "  - $service (端口: $port)"
    done
}

# 主函数
main() {
    case "$1" in
        start)
            start_all
            ;;
        stop)
            stop_all
            ;;
        restart)
            stop_all
            sleep 3
            start_all
            ;;
        status)
            show_status
            ;;
        help|--help|-h)
            show_help
            ;;
        "")
            log_error "请指定操作: start, stop, restart, status, help"
            show_help
            exit 1
            ;;
        *)
            log_error "未知操作: $1"
            show_help
            exit 1
            ;;
    esac
}

# 执行主函数
main "$@"