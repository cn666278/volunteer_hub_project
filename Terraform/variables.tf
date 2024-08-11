variable "flavor" { default = "m1.large" }
variable "image" { default = "Debian 12 Bookworm" } # you may need to change this
variable "name" { default = "SmartTownProjectBuild" }

variable "keypair" { default = "smart_town_keypair" } # you may need to change this
variable "network" { default = "smart_town_network" }   # you need to change this

variable "pool" { default = "cscloud_private_floating" }
variable "server_script" { default = "./server1.sh" }
variable "security_description" { default = "Terraform security group" }
variable "security_name" { default = "smartTowntf_security" }

