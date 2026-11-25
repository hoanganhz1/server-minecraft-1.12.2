import sys, socket, struct

def rcon_command(cmd, host='127.0.0.1', port=25575, password='skyblockrcon123'):
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.settimeout(10)
    sock.connect((host, port))
    
    # Auth
    req_id = 1
    auth_pkg = struct.pack('<iii', len(password) + 10, req_id, 3) + password.encode('utf-8') + b'\x00\x00'
    sock.sendall(auth_pkg)
    resp = sock.recv(4096)
    
    # Command
    req_id = 2
    cmd_pkg = struct.pack('<iii', len(cmd) + 10, req_id, 2) + cmd.encode('utf-8') + b'\x00\x00'
    sock.sendall(cmd_pkg)
    resp = sock.recv(4096)
    
    # Parse response
    length, res_id, type_ = struct.unpack('<iii', resp[:12])
    body = resp[12:-2].decode('utf-8', errors='ignore')
    sock.close()
    return body

if __name__ == '__main__':
    cmd = " ".join(sys.argv[1:]) if len(sys.argv) > 1 else "tps"
    print(rcon_command(cmd))
