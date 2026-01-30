#!/bin/bash

# Must be run as root
if [ "$EUID" -ne 0 ]; then
  echo "Please run as root using sudo"
  exit 1
fi

# -------- GROUP CREATION --------
while true; do
  read -p "Enter group name: " groupname
  if getent group "$groupname" > /dev/null; then
    echo "Group already exists. Try another name."
  else
    groupadd "$groupname"
    echo "Group '$groupname' created."
    break
  fi
done

# -------- USER CREATION --------
while true; do
  read -p "Enter username: " username
  if getent passwd "$username" > /dev/null; then
    echo "User already exists. Try another username."
  else
    useradd -m -s /bin/bash -g "$groupname" "$username"
    echo "User '$username' created."
    break
  fi
done

# -------- PASSWORD --------
echo "Set password for $username"
passwd "$username"

# -------- DIRECTORY --------
mkdir "/$username"
chown "$username:$groupname" "/$username"

# -------- PERMISSIONS --------
chmod 770 "/$username"     # full control for owner & group
chmod +t "/$username"      # only owner can delete files

echo "User management completed successfully."
