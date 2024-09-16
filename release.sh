#!/bin/bash -i

#
# Copyright 2024. IT-Systemhaus der Bundesagentur fuer Arbeit
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

set -e

bump_type=$1

if [[ -z $bump_type ]]; then
  read -p "Enter the version bump type (major, minor, patch): " bump_type

  if [[ "$bump_type" != "major" && "$bump_type" != "minor" && "$bump_type" != "patch" ]]; then
    echo "Invalid bump type. Please enter 'major', 'minor', or 'patch'."
    exit 1
  fi
fi

if [[ -z $MAVEN_SETTINGS ]]; then
  CONFIGURED_MVN="mvn"
else
  CONFIGURED_MVN="mvn --settings $MAVEN_SETTINGS"
fi

# Remove -SNAPSHOT from the version if it is there
current_version=$($CONFIGURED_MVN help:evaluate -Dexpression=project.version -q -DforceStdout)
base_version=${current_version/-SNAPSHOT/}

# Split ba-version and keycloak-version
IFS='-' read -ra base_version_parts <<< "$base_version"
ba_version=${base_version_parts[0]}
keycloak_version=${base_version_parts[1]}

# Treat the version as SemVer and update major, minor, or patch (user choice)
IFS='.' read -ra version_parts <<< "$ba_version"
major=${version_parts[0]}
minor=${version_parts[1]}
patch=${version_parts[2]}

case $bump_type in
  major)
    major=$((major+1))
    minor=0
    patch=0
    ;;
  minor)
    minor=$((minor+1))
    patch=0
    ;;
  patch)
    patch=$((patch)) # Wird bereits im letzten Release als SNAPSHOT gesetzt
    ;;
esac

new_version="$major.$minor.$patch-$keycloak_version"

$CONFIGURED_MVN versions:set -DnewVersion="$new_version"

# Commit the changes and create a git tag
gh add .
gh commit -m "chore(release): release version $new_version"
gh tag "v$new_version"
gh push
gh push --tags

# Set the next SNAPSHOT version
next_snapshot_version="$major.$minor.$((patch+1))-$keycloak_version-SNAPSHOT"
$CONFIGURED_MVN versions:set -DnewVersion="$next_snapshot_version"

# Commit and push the changes
gh add .
gh commit -m "chore(mvn): set version to $next_snapshot_version"
gh push
